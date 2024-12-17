package com.igeek.zncq.mapper;

import com.igeek.zncq.entity.Container;
import com.igeek.zncq.entity.ContainerExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface ContainerMapper {
    int countByExample(ContainerExample example);

    int deleteByExample(ContainerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Container record);

    int insertSelective(Container record);

    List<Container> selectByExample(ContainerExample example);

    Container selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Container record, @Param("example") ContainerExample example);

    int updateByExample(@Param("record") Container record, @Param("example") ContainerExample example);

    int updateByPrimaryKeySelective(Container record);

    int updateByPrimaryKey(Container record);

    int countByIdWarehouseContainer(Integer[] ids);

    int deleteByIdWarehouseContainer(Integer[] ids);
}