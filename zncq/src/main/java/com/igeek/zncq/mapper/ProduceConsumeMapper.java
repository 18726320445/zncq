package com.igeek.zncq.mapper;

import com.igeek.zncq.entity.ProduceConsume;
import com.igeek.zncq.entity.ProduceConsumeExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface ProduceConsumeMapper {
    int countByExample(ProduceConsumeExample example);

    int deleteByExample(ProduceConsumeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProduceConsume record);

    int insertSelective(ProduceConsume record);

    List<ProduceConsume> selectByExample(ProduceConsumeExample example);

    ProduceConsume selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProduceConsume record, @Param("example") ProduceConsumeExample example);

    int updateByExample(@Param("record") ProduceConsume record, @Param("example") ProduceConsumeExample example);

    int updateByPrimaryKeySelective(ProduceConsume record);

    int updateByPrimaryKey(ProduceConsume record);
}