package com.igeek.zncq.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.zncq.entity.*;
import com.igeek.zncq.exception.AddException;
import com.igeek.zncq.exception.DeleteException;
import com.igeek.zncq.exception.UpdateException;
import com.igeek.zncq.mapper.*;
import com.igeek.zncq.service.IWarehouseService;
import com.igeek.zncq.vo.*;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/14 19:01
 * @email liuyia2022@163.com
 */
@Transactional(rollbackFor = {})
@Service
public class WarehouseServiceImpl implements IWarehouseService {
    @Autowired
    WarehouseMapper warehouseMapper;
    @Autowired
    StockMapper stockMapper;
    @Autowired
    InStorageMapper inStorageMapper;
    @Autowired
    OutStorageMapper outStorageMapper;
    @Autowired
    WarehouseTransferMapper warehouseTransferMapper;
    @Autowired
    ContainerMapper containerMapper;

    @Override
    public PageVo<Warehouse> selectWarehouse(Integer pageNum) {
        PageVo<Warehouse> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum,7);
        List<Warehouse> warehouses = warehouseMapper.selectByExample(new WarehouseExample());
        PageInfo<Warehouse> pageInfo = new PageInfo<>(warehouses, 5);
        pageVo.setData(pageInfo.getList());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        return pageVo;
    }

    @Override
    public PageVo<Warehouse> selectWarehouseByQuery(WarehouseQueryVo warehouseQueryVo) {
        PageVo<Warehouse> pageVo = new PageVo<>();
        PageHelper.startPage(warehouseQueryVo.getPageNum(),7);
        WarehouseExample warehouseExample = new WarehouseExample();
        WarehouseExample.Criteria criteria = warehouseExample.createCriteria();
        if (StringUtils.hasLength(warehouseQueryVo.getName())){
            criteria.andNameLike("%"+warehouseQueryVo.getName()+"%");
        }
        if (StringUtils.hasLength(warehouseQueryVo.getAdmin())){
            criteria.andAdminLike("%"+warehouseQueryVo.getAdmin()+"%");
        }
        if (StringUtils.hasLength(warehouseQueryVo.getAddress())){
            criteria.andAddressLike("%"+warehouseQueryVo.getAddress()+"%");
        }
        List<Warehouse> warehouses = warehouseMapper.selectByExample(warehouseExample);
        PageInfo<Warehouse> pageInfo = new PageInfo<>(warehouses, 5);
        pageVo.setData(pageInfo.getList());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setPageSize(pageInfo.getPageSize());
        return pageVo;
    }

    @Override
    public Warehouse selectWarehouseOneById(Integer id) {
        Warehouse warehouse = warehouseMapper.selectByPrimaryKey(id);
        return warehouse;
    }

    @Override
    public void insertWarehouse(Warehouse warehouse) {
        int res = warehouseMapper.insertSelective(warehouse);
        if (res != 1){
            throw new AddException("添加失败,发生未知异常");
        }
    }

    @Override
    public void deleteWarehouseById(Integer[] ids) {
        if (ids.length == 0){
            throw new DeleteException("请不要传入空id值");
        }
        boolean flag = true;
        //看仓库中是否有货物
        for (Integer id : ids) {
            StockExample stockExample = new StockExample();
            stockExample.createCriteria().andWarehouseIdEqualTo(id).andNumNotEqualTo(Long.valueOf(0));
            int count = stockMapper.countByExample(stockExample);
            if (count != 0){
                flag = false;
                break;
            }
        }
        if (!flag){
            throw new DeleteException("删除的仓库中正在被其他货物占有，请先清除货物");
        }
        //查看仓库中是否有正在入库的货物
        InStorageExample inStorageExample = new InStorageExample();
        inStorageExample.createCriteria().andIndateIsNull().andWarehouseIdIn(Arrays.asList(ids));
        int count = inStorageMapper.countByExample(inStorageExample);
        if (count != 0){
            throw new DeleteException("有货物正在入库无法删除");
        }
        //删除库存中所有该仓库的记录
        StockExample stockExample = new StockExample();
        stockExample.clear();
        stockExample.createCriteria().andWarehouseIdIn(Arrays.asList(ids));
        stockMapper.deleteByExample(stockExample);
        //删除入库出库表中的记录
        inStorageExample.clear();
        inStorageExample.createCriteria().andWarehouseIdIn(Arrays.asList(ids));
        inStorageMapper.deleteByExample(inStorageExample);
        OutStorageExample outStorageExample = new OutStorageExample();
        outStorageExample.createCriteria().andWarehouseIdIn(Arrays.asList(ids));
        outStorageMapper.deleteByExample(outStorageExample);
        //删除移库中的记录
        WarehouseTransferExample warehouseTransferExample = new WarehouseTransferExample();
        warehouseTransferExample.createCriteria().andTransferWarehouseIdIn(Arrays.asList(ids));
        WarehouseTransferExample warehouseTransferExample1 = new WarehouseTransferExample();
        warehouseTransferExample.or(warehouseTransferExample1.createCriteria().andOriginalWarehouseIdIn(Arrays.asList(ids)));
        warehouseTransferMapper.deleteByExample(warehouseTransferExample);
        //删除关系表中的记录
        warehouseMapper.deleteByIdForWarehouseAndContainer(ids);
        //最终删除
        WarehouseExample example = new WarehouseExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        int res = warehouseMapper.deleteByExample(example);
        if (res != ids.length) {
            throw new DeleteException("删除失败,发生未知异常");
        }
    }

    @Override
    public void updateWarehouseById(Warehouse warehouse) {
        int res = warehouseMapper.updateByPrimaryKeySelective(warehouse);
        if (res != 1){
            throw new UpdateException("修改失败,发生未知异常");
        }
    }

    @Override
    public WarehouseVo selectContainerByWarehouseId(Integer id) {
        WarehouseVo warehouseVo = warehouseMapper.selectWarehouseVoById(id);
        return warehouseVo;
    }

    @Override
    public void insertWarehouseAndContainer(WarehouseAndContainerVo warehouseAndContainerVo) {
        int res = warehouseMapper.insertWarehouseContainer(warehouseAndContainerVo);
        if (res != 1){
            throw new AddException("添加失败，发生未知异常");
        }
        //库存中也要对应添加
        Stock stock = new Stock();
        stock.setWarehouseId(warehouseAndContainerVo.getWarehouseId());
        stock.setContainerId(warehouseAndContainerVo.getContainerId());
        int i = stockMapper.insertSelective(stock);
        if (i != 1){
            throw new AddException("添加失败，发生未知异常");
        }
    }

    @Override
    public void deleteWarehouseContainerById(Integer warehouseId, Integer containerId) {
        //查看容器中是否有货物
        StockExample stockExample = new StockExample();
        stockExample.createCriteria().andWarehouseIdEqualTo(warehouseId).andContainerIdEqualTo(containerId).andNumNotEqualTo(Long.valueOf(0));
        List<Stock> stocks = stockMapper.selectByExample(stockExample);
        if (stocks.size() != 0){
            throw new DeleteException("该仓库中该容器还存在货物,无法删除");
        }
        stockExample.clear();
        stockExample.createCriteria().andContainerIdEqualTo(containerId).andWarehouseIdEqualTo(warehouseId);
        stockMapper.deleteByExample(stockExample);
        //删除出库入库记录
        InStorageExample inStorageExample = new InStorageExample();
        inStorageExample.createCriteria().andWarehouseIdEqualTo(warehouseId).andContainerIdEqualTo(containerId);
        inStorageMapper.deleteByExample(inStorageExample);
        OutStorageExample outStorageExample = new OutStorageExample();
        outStorageExample.createCriteria().andWarehouseIdEqualTo(warehouseId).andContainerIdEqualTo(containerId);
        outStorageMapper.deleteByExample(outStorageExample);
        //删除移库
        WarehouseTransferExample warehouseTransferExample = new WarehouseTransferExample();
        warehouseTransferExample.createCriteria().andTransferWarehouseIdEqualTo(warehouseId).andTransferContainerIdEqualTo(containerId);
        warehouseTransferMapper.deleteByExample(warehouseTransferExample);
        warehouseTransferExample.clear();
        warehouseTransferExample.createCriteria().andOriginalWarehouseIdEqualTo(warehouseId).andOriginalContainerIdEqualTo(containerId);
        warehouseTransferMapper.deleteByExample(warehouseTransferExample);
        //最终删除
        int res = warehouseMapper.deleteWarehouseContainerById(warehouseId, containerId);
        if (res != 1){
            throw new DeleteException("删除失败，发生未知异常");
        }
    }

    @Override
    public void updateWarehouseContainerById(WarehouseAndContainerVo warehouseAndContainerVo) {
        //先找出容器的最大规格
        Container container = containerMapper.selectByPrimaryKey(warehouseAndContainerVo.getContainerId());
        Long maxCapacity = container.getMaxCapacity();
        //查看库存容量
        StockExample stockExample = new StockExample();
        stockExample.createCriteria().andWarehouseIdEqualTo(warehouseAndContainerVo.getWarehouseId()).andContainerIdEqualTo(warehouseAndContainerVo.getContainerId());
        List<Stock> stocks = stockMapper.selectByExample(stockExample);
        //判断修改数量是否合理
        if (stocks.size() != 0 && warehouseAndContainerVo.getNum()*maxCapacity <= stocks.get(0).getNum()){
            throw new UpdateException("你的修改不合理,仓库中的货物数量大于你所选这的配置最大值，请重新选择");
        }
        //若没库存或设置合理 则修改
        int res = warehouseMapper.updateNumByWarehouseContainerId(warehouseAndContainerVo);
        if (res != 1){
            throw new UpdateException("修改失败，发生未知异常");
        }

    }

    @Override
    public List<Warehouse> selectAll() {
        return warehouseMapper.selectByExample(new WarehouseExample());
    }

    @Override
    public List<Warehouse> selectAllByUse() {
        List<Warehouse> warehouses = warehouseMapper.selectAllByUse();
        return warehouses;
    }

    @Override
    public List<Container> findGoodAllByType(Integer warehouseId, String type) {
        List<Container> containers = warehouseMapper.findGoodAllByType(warehouseId,type);
        return containers;
    }

    @Override
    public Good findGoodByWidAndCid(Integer warehouseId, Integer containerId) {
      return  warehouseMapper.findGoodByWidAndCid(warehouseId,containerId);
    }

    @Override
    public Integer countByWarn() {
       List<Integer> count =  warehouseMapper.countByWarn();
        return count.size();
    }
}
