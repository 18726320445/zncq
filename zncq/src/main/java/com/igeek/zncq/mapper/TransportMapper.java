package com.igeek.zncq.mapper;

import com.igeek.zncq.entity.Transport;
import com.igeek.zncq.entity.TransportExample;
import java.util.List;

import com.igeek.zncq.vo.TransportQueryVo;
import com.igeek.zncq.vo.TransportVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface TransportMapper {
    int countByExample(TransportExample example);

    int deleteByExample(TransportExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Transport record);

    int insertSelective(Transport record);

    List<Transport> selectByExample(TransportExample example);

    Transport selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Transport record, @Param("example") TransportExample example);

    int updateByExample(@Param("record") Transport record, @Param("example") TransportExample example);

    int updateByPrimaryKeySelective(Transport record);

    int updateByPrimaryKey(Transport record);

    List<TransportVo> selectAllByGtState(@Param("state") Integer state);

    List<TransportVo> selectAllByGtStateQuery(@Param("queryVo") TransportQueryVo queryVo);

}