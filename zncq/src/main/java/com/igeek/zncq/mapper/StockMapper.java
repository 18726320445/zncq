package com.igeek.zncq.mapper;

import com.igeek.zncq.entity.Stock;
import com.igeek.zncq.entity.StockExample;
import java.util.List;
import java.util.Map;

import com.igeek.zncq.vo.StockQueryVo;
import com.igeek.zncq.vo.StockVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface StockMapper {
    int countByExample(StockExample example);

    int deleteByExample(StockExample example);

    int deleteByPrimaryKey(Integer stockId);

    int insert(Stock record);

    int insertSelective(Stock record);

    List<Stock> selectByExample(StockExample example);

    Stock selectByPrimaryKey(Integer stockId);

    int updateByExampleSelective(@Param("record") Stock record, @Param("example") StockExample example);

    int updateByExample(@Param("record") Stock record, @Param("example") StockExample example);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);

    List<StockVo> selectAllByPage();

    List<StockVo> selectAllByQuery(StockQueryVo queryVo);

    List<StockVo> selectAllEquipByWC();

    List<StockVo> selectAllEquipByWCAndQuery(@Param("goodName") String goodName, @Param("warehouseName") String warehouseName);
    @MapKey("")
    List<Map<String, Object>> findAllByGoodType(@Param("typeId") Integer typeId);

    List<StockVo> warn();
}