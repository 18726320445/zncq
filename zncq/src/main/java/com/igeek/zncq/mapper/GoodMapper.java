package com.igeek.zncq.mapper;

import com.igeek.zncq.entity.Good;
import com.igeek.zncq.entity.GoodExample;
import java.util.List;

import com.igeek.zncq.vo.GoodQuery;
import com.igeek.zncq.vo.GoodRawVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface GoodMapper {
    int countByExample(GoodExample example);

    int deleteByExample(GoodExample example);

    int deleteByPrimaryKey(Integer goodId);

    int insert(Good record);

    int insertSelective(Good record);

    List<Good> selectByExampleWithBLOBs(GoodExample example);

    List<Good> selectByExample(GoodExample example);

    Good selectByPrimaryKey(Integer goodId);

    int updateByExampleSelective(@Param("record") Good record, @Param("example") GoodExample example);

    int updateByExampleWithBLOBs(@Param("record") Good record, @Param("example") GoodExample example);

    int updateByExample(@Param("record") Good record, @Param("example") GoodExample example);

    int updateByPrimaryKeySelective(Good record);

    int updateByPrimaryKeyWithBLOBs(Good record);

    int updateByPrimaryKey(Good record);

    List<GoodRawVo> selectGoodForRawVoAll();

    List<GoodRawVo> selectGoodRawByGoodQuery(GoodQuery goodQuery);

    int countByGoodIdAndOrder(Integer[] ids);

    List<GoodRawVo> selectGoodRawVoByTypeId(@Param("typeId") int typeId);

}