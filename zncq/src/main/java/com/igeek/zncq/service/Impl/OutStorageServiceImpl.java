package com.igeek.zncq.service.Impl;

import cn.hutool.core.lang.hash.Hash;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.zncq.entity.*;
import com.igeek.zncq.exception.UpdateException;
import com.igeek.zncq.mapper.*;
import com.igeek.zncq.service.IOutStorageService;
import com.igeek.zncq.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/28 09:19
 * @email liuyia2022@163.com
 */
@Service
@Transactional(rollbackFor = {})
public class OutStorageServiceImpl implements IOutStorageService {
    @Autowired
    OutStorageMapper outStorageMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ContainerMapper containerMapper;
    @Autowired
    StockMapper stockMapper;
    @Autowired
    VehicleMapper vehicleMapper;
    @Autowired
    TransportMapper transportMapper;
    @Autowired
    JedisPool jedisPool;
    @Override
    public PageVo<OutStorageVo> selectAllByPage(Integer pageNum) {
        PageVo<OutStorageVo> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum,7);
        List<OutStorageVo> outStorageVos = outStorageMapper.selectAllByPage();
        PageInfo<OutStorageVo> pageInfo = new PageInfo<>(outStorageVos, 5);
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(pageInfo.getList());
        pageVo.setPageSize(pageInfo.getPageSize());
        return pageVo;
    }

    @Override
    public OrderVo findOrderVoByOrderNo(String orderNo) {
        OrderVo orderVo = outStorageMapper.selectOrderVoByOrderNo(orderNo);
        long totalNum = orderVo.getGoods().stream().mapToLong(GoodRawVo::getNum).reduce(Long::sum).orElse(-1);
        orderVo.setTotalNum(totalNum);
        return orderVo;
    }

    @Override
    public PageVo<OutStorageVo> selectAllByQueryPage(OutStorageQueryVo queryVo) {
        PageVo<OutStorageVo> pageVo = new PageVo<>();
        PageHelper.startPage(queryVo.getPageNum(),7);
        List<OutStorageVo> outStorageVos = outStorageMapper.selectAllByQueryPage(queryVo);
        PageInfo<OutStorageVo> pageInfo = new PageInfo<>(outStorageVos, 5);
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(pageInfo.getList());
        pageVo.setPageSize(pageInfo.getPageSize());
        return pageVo;
    }

    @Override
    public PageVo<OutStorageVo> findFinishOutStoragePage(Integer pageNum) {
        PageVo<OutStorageVo> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum,7);
        List<OutStorageVo> outStorageVos = outStorageMapper.selectFinishOutStorage();
        PageInfo<OutStorageVo> pageInfo = new PageInfo<>(outStorageVos, 5);
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(pageInfo.getList());
        pageVo.setPageSize(pageInfo.getPageSize());
        return pageVo;
    }

    @Override
    public void outBound(OutStorageDto outStorageDto) {
        //查看该订单是否有该货物
        String orderNo = outStorageDto.getOrderNo();
        Integer goodId = outStorageDto.getGoodId();
        OutStorageExample outStorageExample = new OutStorageExample();
        outStorageExample.createCriteria().andOrderNoEqualTo(orderNo).andGoodIdEqualTo(goodId);
        List<OutStorage> outStorages = outStorageMapper.selectByExample(outStorageExample);
        if (outStorages == null|| outStorages.size()==0){
            throw new UpdateException("该订单的该货物没有通知出库");
        }
        OutStorage outStorage = outStorages.get(0);
        if (outStorage.getOutdate() != null){
            throw new UpdateException("该订单的该货物已经登记过了,无法重复操作");
        }
        if (!outStorage.getNum().equals(outStorageDto.getNum())){
            throw new UpdateException("登记数量和预期的数量不符合,请核对后在登记");
        }
        AtomicReference<Vehicle> vehicle = new AtomicReference<>();
        List<OutStorageVo> outStorageVos = outStorageMapper.selectAllByPage();
        outStorageVos.stream().forEach(outStorageVo -> {
            if (outStorageVo.getOrderNo().equals(outStorageDto.getOrderNo())){
                vehicle.set(outStorageVo.getVehicle());
                if (!outStorageVo.getVehicle().getVehicleNo().equals(outStorageDto.getVehicleNo())){
                    throw new UpdateException("登记运输车辆和预期不符合,请核对后在登记");
                }
            }
        });
        StockExample stockExample = new StockExample();
        stockExample.createCriteria().andWarehouseIdEqualTo(outStorageDto.getWarehouseId()).andContainerIdEqualTo(outStorageDto.getContainerId());
        List<Stock> stocks = stockMapper.selectByExample(stockExample);
        if (stocks == null || stocks.size()==0){
            throw new UpdateException("该容器不存在请刷新页面");
        }
        Stock stock = stocks.get(0);
        if (stock.getGoodId() == null){
            throw new UpdateException("该容器中没有货物");
        }
        if (!stock.getGoodId().equals(outStorageDto.getGoodId())){
            throw new UpdateException("该容器中存储的货物不一致,不允许存入该容器");
        }
        Long num = stock.getNum();
        if (num < outStorageDto.getNum()){
            throw new UpdateException("该容器的库存不够");
        }
        //开始出库
        outStorage.setContainerId(outStorageDto.getContainerId());
        outStorage.setWarehouseId(outStorageDto.getWarehouseId());
        outStorage.setOutdate(outStorageDto.getOutdate());
        outStorage.setOutId(null);
        int res = outStorageMapper.updateByExampleSelective(outStorage, outStorageExample);
        if (res != 1){
            throw new UpdateException("更新失败,服务器异常");
        }
        //库存变化
        stock.setNum(num - outStorageDto.getNum());
        int i = stockMapper.updateByExampleSelective(stock, stockExample);
        if (i != 1){
            throw new UpdateException("更新失败,服务器异常");
        }
        //派车更新
        OrderVo orderVo = orderMapper.selectOneByOrderNo(orderNo);
        Transport transport = new Transport();
        transport.setState(1);
        transport.setStartAddress("江苏无锡");
        transport.setMidAddress("江苏无锡");
        transport.setEndAddress(orderVo.getCustomer().getAddress());
        Date outdate = outStorageDto.getOutdate();
        transport.setStartdate(outdate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate parse = LocalDate.parse(sdf.format(outdate));
        LocalDate plus = parse.plus(7, ChronoUnit.DAYS);
        String endDate = plus.toString();
        try {
            transport.setExpectedEnddate(sdf.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Vehicle vehicle1 = vehicle.get();
        TransportExample transportExample = new TransportExample();
        transportExample.createCriteria().andVehicleIdEqualTo(vehicle1.getId());
        int ans = transportMapper.updateByExampleSelective(transport, transportExample);
        if (ans != 1){
            throw new UpdateException("更新失败,服务器异常");
        }
        //同时redis也添加缓存
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.set("transport_" + transport.getId() + "_midAddress",transport.getMidAddress());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    @Override
    public PageVo<OutStorageVo> findFinishOutStoragePageByQuery(OutStorageQueryVo queryVo) {
        PageVo<OutStorageVo> pageVo = new PageVo<>();
        PageHelper.startPage(queryVo.getPageNum(),7);
        List<OutStorageVo> outStorageVos = outStorageMapper.selectFinishOutStorageByQuery(queryVo);
        PageInfo<OutStorageVo> pageInfo = new PageInfo<>(outStorageVos, 5);
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(pageInfo.getList());
        pageVo.setPageSize(pageInfo.getPageSize());
        return pageVo;
    }

    @Override
    public List<OutStorage> findByNullDate() {
        OutStorageExample outStorageExample = new OutStorageExample();
        outStorageExample.createCriteria().andOutdateIsNull();
        List<OutStorage> outStorages = outStorageMapper.selectByExample(outStorageExample);
        return outStorages;
    }

    @Override
    public Map<String, Integer> findPreWeekGoodSumTopSeven() {
        List<Map<String,Object>> maps = outStorageMapper.SelectPreWeekGoodSumTopSeven();
        HashMap<String, Integer> map = new HashMap<>();
        maps.stream().forEach(stringIntegerMap -> {
            map.put(String.valueOf((String)stringIntegerMap.get("goodName")),((BigDecimal)stringIntegerMap.get("num")).intValue());
        });
        return map;
    }


}
