package com.igeek.zncq.mapper;

import com.igeek.zncq.entity.Vehicle;
import com.igeek.zncq.entity.VehicleExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface VehicleMapper {
    int countByExample(VehicleExample example);

    int deleteByExample(VehicleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Vehicle record);

    int insertSelective(Vehicle record);

    List<Vehicle> selectByExample(VehicleExample example);

    Vehicle selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Vehicle record, @Param("example") VehicleExample example);

    int updateByExample(@Param("record") Vehicle record, @Param("example") VehicleExample example);

    int updateByPrimaryKeySelective(Vehicle record);

    int updateByPrimaryKey(Vehicle record);

    int deleteByIdForOrderGood(Integer[] ids);

    int addVehicleState(@Param("vehicleId") Integer vehicleId);

    int updateVehicleState(@Param("vehicleId")Integer vehicleId , @Param("state") Integer state);

    List<Vehicle> selectByMaxNum(@Param("maxNum") Long maxNum);

    List<Vehicle> selectAll();

    List<Vehicle> selectAll2();
}