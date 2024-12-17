package com.igeek.zncq.mapper;

import com.igeek.zncq.entity.Purchase;
import com.igeek.zncq.entity.PurchaseExample;
import java.util.List;

import com.igeek.zncq.vo.PurchaseDto;
import com.igeek.zncq.vo.PurchaseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PurchaseMapper {
    int countByExample(PurchaseExample example);

    int deleteByExample(PurchaseExample example);

    int insert(Purchase record);

    int insertSelective(Purchase record);

    List<Purchase> selectByExample(PurchaseExample example);

    int updateByExampleSelective(@Param("record") Purchase record, @Param("example") PurchaseExample example);

    int updateByExample(@Param("record") Purchase record, @Param("example") PurchaseExample example);

    int insertPurchase(Purchase purchase);

    Purchase selectOneByPurchaseNo(@Param("purchaseNo") String purchaseNo);

    PurchaseVo selectPurchaseVoByNo(@Param("purchaseNo") String purchaseNo);

    Integer selectNumOrderGood(@Param("orderNo") String orderNo,@Param("goodId")Integer goodId);

    int updateNumOrderGood(@Param("orderNo") String orderNo,@Param("goodId")Integer goodId,@Param("num") Long num);

    int deleteOrderGoodByQuery(@Param("orderNo") String orderNo,@Param("goodId")Integer goodId);

    int updateState(@Param("purchaseNo") String purchaseNo,@Param("state") Integer state);

    @Update("update purchase set process_name = #{processName} where id = #{id}")
    int updateProcessName(Purchase purchase);
}