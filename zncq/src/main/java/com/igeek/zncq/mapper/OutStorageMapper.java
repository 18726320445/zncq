package com.igeek.zncq.mapper;

import com.igeek.zncq.entity.OutStorage;
import com.igeek.zncq.entity.OutStorageExample;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.igeek.zncq.vo.OrderVo;
import com.igeek.zncq.vo.OutStorageQueryVo;
import com.igeek.zncq.vo.OutStorageVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface OutStorageMapper {
    int countByExample(OutStorageExample example);

    int deleteByExample(OutStorageExample example);

    int deleteByPrimaryKey(Integer outId);

    int insert(OutStorage record);

    int insertSelective(OutStorage record);

    List<OutStorage> selectByExample(OutStorageExample example);

    OutStorage selectByPrimaryKey(Integer outId);

    int updateByExampleSelective(@Param("record") OutStorage record, @Param("example") OutStorageExample example);

    int updateByExample(@Param("record") OutStorage record, @Param("example") OutStorageExample example);

    int updateByPrimaryKeySelective(OutStorage record);

    int updateByPrimaryKey(OutStorage record);

    List<OutStorageVo> selectAllByPage();

    OrderVo selectOrderVoByOrderNo(String orderNo);

    List<OutStorageVo> selectAllByQueryPage(OutStorageQueryVo queryVo);

    List<OutStorageVo> selectFinishOutStorage();

    List<OutStorageVo> selectFinishOutStorageByQuery(OutStorageQueryVo queryVo);
    @MapKey("")
    List<Map<String, Object>> SelectPreWeekGoodSumTopSeven();
}