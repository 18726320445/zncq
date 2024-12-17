package com.igeek.zncq.mapper;

import com.igeek.zncq.entity.InStorage;
import com.igeek.zncq.entity.InStorageExample;
import java.util.List;

import com.igeek.zncq.vo.GoodRawVo;
import com.igeek.zncq.vo.InStorageQueryVo;
import com.igeek.zncq.vo.InStorageVo;
import com.igeek.zncq.vo.OrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface InStorageMapper {
    int countByExample(InStorageExample example);

    int deleteByExample(InStorageExample example);

    int insert(InStorage record);

    int insertSelective(InStorage record);

    List<InStorage> selectByExample(InStorageExample example);

    int updateByExampleSelective(@Param("record") InStorage record, @Param("example") InStorageExample example);

    int updateByExample(@Param("record") InStorage record, @Param("example") InStorageExample example);

    List<InStorageVo> selectAll();

    OrderVo selectAllGoodByOrderNo(@Param("orderNo") String orderNo);

    List<InStorageVo> selectAllByQuery(InStorageQueryVo queryVo);

    List<InStorageVo> selectInStorageVoByInDate();

    @Select("select * from in_storage where orede_no = #{orderNo}")
    InStorage selectOneByOrderNo(@Param("orderNo") String orderNo);

    List<InStorageVo> selectInStorageVoByQuery2(InStorageQueryVo queryVo);

    List<GoodRawVo> selectGoodVoByOrderNo(@Param("orderNo") String orderNo);

    List<GoodRawVo> selectGoodVoByInStorageOrderNo(@Param("orderNo") String orderNo);
}