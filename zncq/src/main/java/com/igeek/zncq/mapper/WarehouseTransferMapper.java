package com.igeek.zncq.mapper;

import com.igeek.zncq.entity.WarehouseTransfer;
import com.igeek.zncq.entity.WarehouseTransferExample;
import java.util.List;

import com.igeek.zncq.vo.WarehouseTransferQueryVo;
import com.igeek.zncq.vo.WarehouseTransferVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface WarehouseTransferMapper {
    int countByExample(WarehouseTransferExample example);

    int deleteByExample(WarehouseTransferExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WarehouseTransfer record);

    int insertSelective(WarehouseTransfer record);

    List<WarehouseTransfer> selectByExample(WarehouseTransferExample example);

    WarehouseTransfer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WarehouseTransfer record, @Param("example") WarehouseTransferExample example);

    int updateByExample(@Param("record") WarehouseTransfer record, @Param("example") WarehouseTransferExample example);

    int updateByPrimaryKeySelective(WarehouseTransfer record);

    int updateByPrimaryKey(WarehouseTransfer record);

    List<WarehouseTransferVo> findAll();

    List<WarehouseTransferVo> findAllByQuery(WarehouseTransferQueryVo queryVo);
}