package com.igeek.zncq.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.zncq.entity.*;
import com.igeek.zncq.exception.AddException;
import com.igeek.zncq.exception.UpdateException;
import com.igeek.zncq.mapper.*;
import com.igeek.zncq.service.IWarehouseTransferService;
import com.igeek.zncq.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/1 20:12
 * @email liuyia2022@163.com
 */
@Service
@Transactional(rollbackFor = {})
public class WarehouseTransferServiceImpl implements IWarehouseTransferService {
    @Autowired
    WarehouseTransferMapper warehouseTransferMapper;
    @Autowired
    GoodMapper goodMapper;
    @Autowired
    ContainerMapper containerMapper;
    @Autowired
    StockMapper stockMapper;
    @Autowired
    WarehouseMapper warehouseMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OutStorageMapper outStorageMapper;
    @Autowired
    InStorageMapper inStorageMapper;
    @Autowired
    TransportMapper transportMapper;
    @Autowired
    VehicleMapper vehicleMapper;
    @Override
    public void transfer(WarehouseTransferDto warehouseTransferDto) {
        //查看货物类型是否和容器匹配
        Integer goodId = warehouseTransferDto.getGoodId();
        Integer transferWarehouseId = warehouseTransferDto.getTransferWarehouseId();
        Integer originalWarehouseId = warehouseTransferDto.getOriginalWarehouseId();
        Integer transferContainerId = warehouseTransferDto.getTransferContainerId();
        Integer originalContainerId = warehouseTransferDto.getOriginalContainerId();
        Long num = warehouseTransferDto.getNum();
        Good good = goodMapper.selectByPrimaryKey(goodId);
        String typeName = null;
        if (good.getGoodTypeId() == 1) {
            typeName = "原料";
        } else if (good.getGoodTypeId() == 2) {
            typeName = "产品";
        } else if (good.getGoodTypeId() == 3) {
            typeName = "设备";
        }
        Container tContainer = containerMapper.selectByPrimaryKey(transferContainerId);
        Container oContainer = containerMapper.selectByPrimaryKey(originalContainerId);
        if (!tContainer.getType().equals(typeName) || !oContainer.getType().equals(typeName)) {
            throw new AddException("货物类型容器不匹配");
        }
        //查看原仓库中是否有足够的库存转移
        StockExample stockExample = new StockExample();
        stockExample.createCriteria().andWarehouseIdEqualTo(originalWarehouseId).andContainerIdEqualTo(originalContainerId);
        List<Stock> stocks = stockMapper.selectByExample(stockExample);
        if (stocks.size() == 0){
            throw new UpdateException("转出仓库没有该容器");
        }
        Stock stock = stocks.get(0);
        if (num > stock.getNum()) {
            throw new AddException("原容器库存不足");
        }
        stockExample.clear();
        stockExample.createCriteria().andWarehouseIdEqualTo(transferWarehouseId).andContainerIdEqualTo(transferContainerId);
        List<Stock> stocks1 = stockMapper.selectByExample(stockExample);
        if (stocks1.size() == 0){
            throw new UpdateException("传入仓库没有该容器");
        }
        Stock stock1 = stocks1.get(0);
        Long containerNum = warehouseMapper.selectContainerNumByWarehouseId(transferContainerId, transferWarehouseId);
        if (num > tContainer.getMaxCapacity() * containerNum - stock1.getNum()) {
            throw new AddException("转入容器剩余容量不足存储当前运输的数量");
        }
        //满足条件移库
        //生成移库订单
        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());
        order.setCustomerId(1);
        order.setOrderType("移库订单");
        order.setNum(num);
        order.setState(2);
        order.setIsDelete(0);
        order.setOriginatorName(warehouseTransferDto.getProcessName());
        order.setProcessName(warehouseTransferDto.getProcessName());
        order.setCreatedate(warehouseTransferDto.getTransferdate());
        int insert = orderMapper.insertOrder(order);
        if (insert != 1) {
            throw new AddException("添加异常,订单创建失败");
        }
        //添加关系表
        int i = orderMapper.insertOrderGood2(order.getOrderNo(), goodId, num, warehouseTransferDto.getVehicleId());
        if (i != 1) {
            throw new AddException("添加异常,订单创建失败");
        }
        //移库信息
        WarehouseTransfer warehouseTransfer = new WarehouseTransfer();
        BeanUtils.copyProperties(warehouseTransferDto,warehouseTransfer);
        warehouseTransfer.setOrderNo(order.getOrderNo());
        int i5 = warehouseTransferMapper.insertSelective(warehouseTransfer);
        if (i5 != 1){
            throw new AddException("移库创建异常，重新操作");
        }
        //生成出库通知
        OutStorage outStorage = new OutStorage();
        outStorage.setOrderNo(order.getOrderNo());
        outStorage.setGoodId(goodId);
        outStorage.setNum(num);
        outStorage.setWarehouseId(originalWarehouseId);
        outStorage.setContainerId(originalContainerId);
        int i1 = outStorageMapper.insertSelective(outStorage);
        if (i1 != 1){
            throw new AddException("添加异常,出库通知创建失败");
        }
        Warehouse warehouse = warehouseMapper.selectByPrimaryKey(originalWarehouseId);
        Warehouse warehouse1 = warehouseMapper.selectByPrimaryKey(transferWarehouseId);
        //生成运单并改变车辆状态
        Transport transport = new Transport();
        transport.setVehicleId(warehouseTransferDto.getVehicleId());
        transport.setState(0);
        transport.setStartAddress(warehouse.getAddress());
        transport.setEndAddress(warehouse1.getAddress());
        int i2 = transportMapper.insertSelective(transport);
        if (i2 != 1){
            throw new AddException("生成运单异常");
        }
        //改变汽车状态
        int i3 = vehicleMapper.updateVehicleState(warehouseTransferDto.getVehicleId(), 1);
        if (i3 != 1){
            throw new UpdateException("运输车辆状态改变异常");
        }
        //生成入库通知
        InStorage inStorage = new InStorage();
        inStorage.setNum(num);
        inStorage.setGoodId(goodId);
        inStorage.setOrderNo(order.getOrderNo());
        inStorage.setWarehouseId(transferWarehouseId);
        inStorage.setContainerId(transferContainerId);
        int i4 = inStorageMapper.insertSelective(inStorage);
        if (i4 != 1){
            throw new AddException("生成入库通知异常");
        }
    }

    @Override
    public PageVo<WarehouseTransferVo> findAll(Integer pageNum) {
        PageVo<WarehouseTransferVo> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum,7);
        List<WarehouseTransferVo> warehouseTransferVos = warehouseTransferMapper.findAll();
        PageInfo<WarehouseTransferVo> pageInfo = new PageInfo<>(warehouseTransferVos,5);
        pageVo.setCurrentPage(pageNum);
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(pageInfo.getList());
        return pageVo;
    }

    @Override
    public PageVo<WarehouseTransferVo> findAllByQuery(WarehouseTransferQueryVo queryVo) {
        PageVo<WarehouseTransferVo> pageVo = new PageVo<>();
        PageHelper.startPage(queryVo.getPageNum(),7);
        List<WarehouseTransferVo> warehouseTransferVos = warehouseTransferMapper.findAllByQuery(queryVo);
        PageInfo<WarehouseTransferVo> pageInfo = new PageInfo<>(warehouseTransferVos,5);
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(pageInfo.getList());
        return pageVo;
    }
}
