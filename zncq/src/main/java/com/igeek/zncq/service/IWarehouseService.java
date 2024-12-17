package com.igeek.zncq.service;

import com.igeek.zncq.entity.Container;
import com.igeek.zncq.entity.Good;
import com.igeek.zncq.entity.Warehouse;
import com.igeek.zncq.vo.*;

import java.util.List;

/**
 * @author liuyi
 */
public interface IWarehouseService {
    /**
     * 查找对应页数的仓库信息
     * @param pageNum
     * @return
     */
    public PageVo<Warehouse> selectWarehouse(Integer pageNum);

    /**
     * 根据查询条件查询仓库信息
     * @param warehouseQueryVo
     * @return
     */
    public PageVo<Warehouse> selectWarehouseByQuery(WarehouseQueryVo warehouseQueryVo);

    /**
     * 根据id查找仓库信息
     * @return
     */
    public Warehouse selectWarehouseOneById(Integer id);

    /**
     * 添加仓库记录
     * @param warehouse
     */
    public void insertWarehouse(Warehouse warehouse);
    /**
     * 根据id批量删除
     * @param ids
     */
    public void deleteWarehouseById(Integer[] ids);

    /**
     * 根据Id修改信息
     * @param warehouse
     */
    public void updateWarehouseById(Warehouse warehouse);

    /**
     * 根据仓库id查找对应的容器
     * @param id
     * @return
     */
    public WarehouseVo selectContainerByWarehouseId(Integer id);

    /**
     * 添加仓库中的容器
     * @param warehouseAndContainerVo
     */
    void insertWarehouseAndContainer(WarehouseAndContainerVo warehouseAndContainerVo);

    /**
     * 删除对应仓库中的容器
     * @param warehouseId
     * @param containerId
     */
    void deleteWarehouseContainerById(Integer warehouseId, Integer containerId);

    /**
     * 根据WarehouseId和ContainerId修改数量
     * @param warehouseAndContainerVo
     */
    void updateWarehouseContainerById(WarehouseAndContainerVo warehouseAndContainerVo);

    /**
     * 查询所有仓库信息
     * @return
     */
    List<Warehouse> selectAll();

    /**
     * 所有正在使用的仓库信息
     * @return
     */
    List<Warehouse> selectAllByUse();

    /**
     * 所有正在使用的仓库中指定容器类型的所有容器信息
     * @param warehouseId
     * @param type
     * @return
     */
    List<Container> findGoodAllByType(Integer warehouseId, String type);

    /**
     * 根据warehouseId containerId 查找对应的库存
     * @param warehouseId
     * @param containerId
     * @return
     */
    Good findGoodByWidAndCid(Integer warehouseId, Integer containerId);

    /**
     * 统计有原料预警的仓库
     * @return
     */
    Integer countByWarn();
}
