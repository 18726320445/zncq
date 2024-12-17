package com.igeek.zncq.mapper;

import com.igeek.zncq.entity.Container;
import com.igeek.zncq.entity.Good;
import com.igeek.zncq.entity.Warehouse;
import com.igeek.zncq.entity.WarehouseExample;
import java.util.List;

import com.igeek.zncq.vo.ContainerVo;
import com.igeek.zncq.vo.WarehouseAndContainerVo;
import com.igeek.zncq.vo.WarehouseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface WarehouseMapper {
    int countByExample(WarehouseExample example);

    int deleteByExample(WarehouseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Warehouse record);

    int insertSelective(Warehouse record);

    List<Warehouse> selectByExample(WarehouseExample example);

    Warehouse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Warehouse record, @Param("example") WarehouseExample example);

    int updateByExample(@Param("record") Warehouse record, @Param("example") WarehouseExample example);

    int updateByPrimaryKeySelective(Warehouse record);

    int updateByPrimaryKey(Warehouse record);

    int deleteByIdForWarehouseAndContainer(Integer[] ids);

    WarehouseVo selectWarehouseVoById(Integer id);

    int insertWarehouseContainer(WarehouseAndContainerVo warehouseAndContainerVo);

    int deleteWarehouseContainerById(@Param("warehouseId") Integer warehouseId,@Param("containerId") Integer containerId);

    int updateNumByWarehouseContainerId(WarehouseAndContainerVo warehouseAndContainerVo);

    Long selectContainerNumByWarehouseId(@Param("containerId") Integer containerId ,@Param("warehouseId") Integer warehouseId);

    List<Warehouse> selectAllByUse();

    List<Container> findGoodAllByType(@Param("warehouseId") Integer warehouseId,@Param("type") String type);

    Good findGoodByWidAndCid(@Param("warehouseId")Integer warehouseId,@Param("containerId") Integer containerId);

    List<Integer> countByWarn();
}