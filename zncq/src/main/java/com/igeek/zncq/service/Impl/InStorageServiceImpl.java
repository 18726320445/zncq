package com.igeek.zncq.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.zncq.entity.*;
import com.igeek.zncq.exception.UpdateException;
import com.igeek.zncq.mapper.*;
import com.igeek.zncq.service.IInStorageService;
import com.igeek.zncq.vo.*;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/23 10:58
 * @email liuyia2022@163.com
 */
@Service
@Transactional(rollbackFor = {})
public class InStorageServiceImpl implements IInStorageService {

    @Autowired
    InStorageMapper inStorageMapper;
    @Autowired
    WarehouseMapper warehouseMapper;
    @Autowired
    StockMapper stockMapper;
    @Autowired
    GoodMapper goodMapper;
    @Autowired
    ContainerMapper containerMapper;
    @Autowired
    OrderMapper orderMapper;

    @Override
    public PageVo<InStorageVo> selectInStorageVoByPage(Integer pageNum) {
        PageVo<InStorageVo> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum, 7);
        List<InStorageVo> inStorageVos = inStorageMapper.selectAll();
        PageInfo<InStorageVo> pageInfo = new PageInfo<>(inStorageVos, 5);
        pageVo.setData(pageInfo.getList());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setPageSize(pageInfo.getPageSize());
        return pageVo;
    }

    @Override
    public OrderVo selectAllGoodByOrderNo(String orderNo) {
        return inStorageMapper.selectAllGoodByOrderNo(orderNo);
    }

    @Override
    public PageVo<InStorageVo> selectInStorageVoByQuery(InStorageQueryVo queryVo) {
        PageVo<InStorageVo> pageVo = new PageVo<>();
        PageHelper.startPage(queryVo.getPageNum(), 7);
        List<InStorageVo> inStorageVos = inStorageMapper.selectAllByQuery(queryVo);
        PageInfo<InStorageVo> pageInfo = new PageInfo<>(inStorageVos, 5);
        pageVo.setData(pageInfo.getList());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setPageSize(pageInfo.getPageSize());
        return pageVo;
    }

    @Override
    public PageVo<InStorageVo> selectInStorageVoByInDate(Integer pageNum) {
        PageVo<InStorageVo> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum, 7);
        List<InStorageVo> inStorageVos = inStorageMapper.selectInStorageVoByInDate();
        PageInfo<InStorageVo> pageInfo = new PageInfo<>(inStorageVos, 5);
        pageVo.setData(pageInfo.getList());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setPageSize(pageInfo.getPageSize());
        return pageVo;
    }

    @Override
    public void register(InStorage inStorage) {
        InStorageExample inStorageExample = new InStorageExample();
        inStorageExample.createCriteria().andOrderNoEqualTo(inStorage.getOrderNo()).andGoodIdEqualTo(inStorage.getGoodId());
        List<InStorage> inStorages = inStorageMapper.selectByExample(inStorageExample);
        if (inStorages == null || inStorages.size() == 0) {
            throw new UpdateException("订单物品没有通知入库,请刷新页面");
        }
        if (inStorages.get(0).getIndate() != null){
            throw new UpdateException("该订单的该货物已经登记过了,无法重复操作");
        }
        if (!inStorages.get(0).getWarehouseId().equals(inStorage.getWarehouseId())){
            throw new UpdateException("与预期的仓库不一致");
        }
        if (!inStorages.get(0).getNum().equals(inStorage.getNum())){
            throw new UpdateException("与预期的数量不一致");
        }
        //查看仓库容器中存储货物是否一致以及剩余容量量是否足够入库
        StockExample stockExample = new StockExample();
        stockExample.createCriteria().andWarehouseIdEqualTo(inStorage.getWarehouseId()).andContainerIdEqualTo(inStorage.getContainerId());
        List<Stock> stocks = stockMapper.selectByExample(stockExample);
        Stock stock = stocks.get(0);
        //查看库存容器是否装了货物没有直接看容量是否足够，如果已经有了货物这判断是否为同一货物
        if (stock.getGoodId() != null){
            if (!stock.getGoodId().equals(inStorage.getGoodId())) {
                throw new UpdateException("该容器中存储的货物不一致,不允许存入该容器");
            }
        }else {
            Good good = goodMapper.selectByPrimaryKey(inStorage.getGoodId());
            Container container = containerMapper.selectByPrimaryKey(inStorage.getContainerId());
            String type = null;
            if (good.getGoodTypeId() == 1){
                type = "原料";
            }else if (good.getGoodTypeId() == 2){
                type = "产品";
            }else{
                type = "设备";
            }
            if (!type.equals(container.getType())){
                throw new UpdateException("该容器中存储的货物类型不一致,不允许存入该容器");
            }
        }
        Long num = stock.getNum();
        AtomicReference<Long> maxNum = new AtomicReference<>();
        WarehouseVo warehouseVo = warehouseMapper.selectWarehouseVoById(inStorage.getWarehouseId());
        warehouseVo.getContainerVos().stream().forEach(containerVo -> {
            if (containerVo.getId().equals(inStorage.getContainerId())) {
                maxNum.set(containerVo.getMaxCapacity() * containerVo.getNum());
            }
        });
        if (maxNum.get() - num < inStorage.getNum()) {
            throw new UpdateException("容器容量不足,请切换容器存储");
        }
        //正式更新入库
        inStorageExample.clear();
        inStorageExample.createCriteria().andOrderNoEqualTo(inStorage.getOrderNo()).andGoodIdEqualTo(inStorage.getGoodId());
        int res = inStorageMapper.updateByExampleSelective(inStorage, inStorageExample);
        if (res != 1) {
            throw new UpdateException("更新失败，请重新操作");
        }
        //订单状态变化
        int i1 = orderMapper.updateStateByOrderNoInt(inStorage.getOrderNo(), 3);
        if (i1 != 1){
            throw new UpdateException("更新失败，请重新操作");
        }
        //库存变化
        stockExample.clear();
        stockExample.createCriteria().andWarehouseIdEqualTo(inStorage.getWarehouseId()).andContainerIdEqualTo(inStorage.getContainerId());
        Stock stock1 = new Stock();
        stock1.setNum(stock.getNum() + inStorage.getNum());
        int i = stockMapper.updateByExampleSelective(stock1, stockExample);
        if (i != 1) {
            throw new UpdateException("更新失败，请重新操作");
        }
    }

    @Override
    public void updateRegist(InStorage inStorage) {
        String orderNo = inStorage.getOrderNo();
        Integer goodId = inStorage.getGoodId();
        Good good = goodMapper.selectByPrimaryKey(goodId);
        Container container = containerMapper.selectByPrimaryKey(inStorage.getContainerId());
        String type = null;
        if (good.getGoodTypeId() == 1){
            type = "原料";
        }else if (good.getGoodTypeId() == 2){
            type = "产品";
        }else{
            type = "设备";
        }
        if (!type.equals(container.getType())){
            throw new UpdateException("该容器中存储的货物类型不一致,不允许存入该容器");
        }
        if (inStorage.getIndate().after(new Date())){
            throw new UpdateException("时间设置错误");
        }
        //比较容量是否足够
        StockExample stockExample = new StockExample();
        stockExample.createCriteria().andWarehouseIdEqualTo(inStorage.getWarehouseId()).andContainerIdEqualTo(inStorage.getContainerId());
        List<Stock> stocks = stockMapper.selectByExample(stockExample);
        Stock stock = stocks.get(0);
        Long num = stock.getNum();
        AtomicReference<Long> maxNum = new AtomicReference<>();
        WarehouseVo warehouseVo = warehouseMapper.selectWarehouseVoById(inStorage.getWarehouseId());
        warehouseVo.getContainerVos().stream().forEach(containerVo -> {
            if (containerVo.getId().equals(inStorage.getContainerId())) {
                maxNum.set(containerVo.getMaxCapacity() * containerVo.getNum());
            }
        });
        if (maxNum.get() - num < inStorage.getNum()) {
            throw new UpdateException("容器容量不足,请切换容器存储");
        }
        //正式更新入库
        InStorageExample inStorageExample = new InStorageExample();
        inStorageExample.createCriteria().andOrderNoEqualTo(orderNo).andGoodIdEqualTo(goodId);
        int res = inStorageMapper.updateByExampleSelective(inStorage, inStorageExample);
        if (res != 1) {
            throw new UpdateException("更新失败，请重新操作");
        }
        //库存变化
        stockExample.clear();
        stockExample.createCriteria().andWarehouseIdEqualTo(inStorage.getWarehouseId()).andContainerIdEqualTo(inStorage.getContainerId());
        Stock stock1 = new Stock();
        stock1.setNum(stock.getNum() + inStorage.getNum());
        int i = stockMapper.updateByExampleSelective(stock1, stockExample);
        if (i != 1) {
            throw new UpdateException("更新失败，请重新操作");
        }

    }

    @Override
    public InStorage selectOneByOrderNoAndGoodId(String orderNo, Integer goodId) {
        InStorageExample inStorageExample = new InStorageExample();
        inStorageExample.createCriteria().andGoodIdEqualTo(goodId).andOrderNoEqualTo(orderNo);
        List<InStorage> inStorages = inStorageMapper.selectByExample(inStorageExample);
        return inStorages.get(0);
    }
    @Override
    public PageVo<InStorageVo> selectInStorageVoByQuery2(InStorageQueryVo queryVo) {
        PageVo<InStorageVo> pageVo = new PageVo<>();
        PageHelper.startPage(queryVo.getPageNum(), 7);
        List<InStorageVo> inStorageVos = inStorageMapper.selectInStorageVoByQuery2(queryVo);
        PageInfo<InStorageVo> pageInfo = new PageInfo<>(inStorageVos, 5);
        pageVo.setData(pageInfo.getList());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setPageSize(pageInfo.getPageSize());
        return pageVo;
    }

    @Override
    public List<InStorage> findByNullDate() {
        InStorageExample inStorageExample = new InStorageExample();
        inStorageExample.createCriteria().andIndateIsNull();
        return inStorageMapper.selectByExample(inStorageExample);
    }
}
