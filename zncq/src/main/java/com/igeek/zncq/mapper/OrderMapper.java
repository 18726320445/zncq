package com.igeek.zncq.mapper;


import com.igeek.zncq.entity.Order;
import com.igeek.zncq.entity.OrderExample;
import java.util.List;
import java.util.Map;


import com.igeek.zncq.vo.OrderGoodVo;
import com.igeek.zncq.vo.OrderVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface OrderMapper {
    int countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int insertOrder(Order order);

    int insertOrderGood(@Param("orderNo") String orderNo,@Param("goodId") Integer goodId,@Param("num") Long num);

    int insertOrderGood2(@Param("orderNo") String orderNo,@Param("goodId") Integer goodId,@Param("num") Long num,@Param("vehicleId") Integer vehicleId);

    Long countNumByOrderNoLong(@Param("orderNo") String orderNo);

    int updateStateByOrderNoInt(@Param("orderNo") String orderNo ,@Param("state") Integer state);

    int updateStateAndProcessNameByOrderNoInt(@Param("orderNo") String orderNo ,@Param("state") Integer state,@Param("processName") String processName);

    int updateIsDeleteByOrderNo(@Param("orderNo") String orderNo);

    int updateNumByOrderNoInt(@Param("orderNo") String orderNo ,@Param("num") Long num , @Param("total") Double total);

    Double selectTotalByOrderNoDouble(@Param("orderNo") String orderNo);

    int deleteByOrderNoInt(@Param("orderNo") String orderNo);

    int countOrderByCustomerIdsAndStates(Integer[] ids, Integer[] states);

    int updateVehicleByOrderNo(@Param("orderNo") String orderNo,@Param("vehicleId")Integer vehicleId);

    OrderVo selectOneByOrderNo(@Param("orderNo") String orderNo);

    OrderGoodVo selectOrderGood(@Param("orderNo") String orderNo, @Param("goodId") Integer goodId);

    List<OrderVo> selectOrderVoAll(@Param("orderNo") String orderNo ,@Param("vehicleNo")String vehicleNo);

    int updateStateOrederGood(@Param("orderNo") String orderNo ,@Param("state")Integer state,@Param("goodId") Integer goodId);

    OrderVo selectOneVehicleByOrderNo(@Param("orderNo")String orderNo);
}