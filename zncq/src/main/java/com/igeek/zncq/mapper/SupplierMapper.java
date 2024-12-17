package com.igeek.zncq.mapper;

import com.igeek.zncq.entity.Supplier;
import com.igeek.zncq.entity.SupplierExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface SupplierMapper {
    int countByExample(SupplierExample example);

    int deleteByExample(SupplierExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Supplier record);

    int insertSelective(Supplier record);

    List<Supplier> selectByExampleWithBLOBs(SupplierExample example);

    List<Supplier> selectByExample(SupplierExample example);

    Supplier selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Supplier record, @Param("example") SupplierExample example);

    int updateByExampleWithBLOBs(@Param("record") Supplier record, @Param("example") SupplierExample example);

    int updateByExample(@Param("record") Supplier record, @Param("example") SupplierExample example);

    int updateByPrimaryKeySelective(Supplier record);

    int updateByPrimaryKeyWithBLOBs(Supplier record);

    int updateByPrimaryKey(Supplier record);
}