package com.igeek.zncq.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.zncq.entity.TransportExample;
import com.igeek.zncq.entity.Vehicle;
import com.igeek.zncq.entity.VehicleExample;
import com.igeek.zncq.exception.AddException;
import com.igeek.zncq.exception.DeleteException;
import com.igeek.zncq.exception.UpdateException;
import com.igeek.zncq.mapper.TransportMapper;
import com.igeek.zncq.mapper.VehicleMapper;
import com.igeek.zncq.service.IVehicleService;
import com.igeek.zncq.vo.PageVo;
import com.igeek.zncq.vo.VehicleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/16 19:27
 * @email liuyia2022@163.com
 */
@Service
@Transactional(rollbackFor = {})
public class VehicleServiceImpl implements IVehicleService {
    @Autowired
    VehicleMapper vehicleMapper;
    @Autowired
    TransportMapper transportMapper;

    @Override
    public PageVo<Vehicle> selectAll(Integer pageNum) {
        PageVo<Vehicle> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum, 7);
        List<Vehicle> vehicles = vehicleMapper.selectByExample(new VehicleExample());
        PageInfo<Vehicle> pageInfo = new PageInfo<>(vehicles, 5);
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(pageInfo.getList());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        return pageVo;
    }

    @Override
    public void insertVehicle(Vehicle vehicle) {
        int res = vehicleMapper.insertSelective(vehicle);
        if (res != 1) {
            throw new AddException("添加失败,服务器发生未知异常");
        }

        //添加状态
        int i = vehicleMapper.addVehicleState(vehicle.getId());
        if (i != 1){
            throw new AddException("添加失败,服务器发生未知异常");
        }
    }

    @Override
    public Vehicle selectOneById(Integer id) {
        Vehicle vehicle = vehicleMapper.selectByPrimaryKey(id);
        return vehicle;
    }

    @Override
    public void updateById(Vehicle vehicle) {
        int res = vehicleMapper.updateByPrimaryKeySelective(vehicle);
        if (res != 1) {
            throw new UpdateException("修改失败,服务器发生未知异常");
        }
    }

    @Override
    public void deleteById(Integer[] ids) {
        if (ids.length == 0) {
            throw new DeleteException("请不要输入空id");
        }
        TransportExample transportExample = new TransportExample();
        transportExample.createCriteria().andVehicleIdIn(Arrays.asList(ids)).andStateIn(Arrays.asList(new Integer[]{0, 1}));
        int count = transportMapper.countByExample(transportExample);
        if (count != 0) {
            throw new DeleteException("该车辆有运输计划，无法删除");
        }
        //如果对应车辆没有运输计划的则可以删除
        //删除运输记录
        transportExample.clear();
        transportExample.createCriteria().andVehicleIdIn(Arrays.asList(ids));
        transportMapper.deleteByExample(transportExample);
        //删除对应的订单记录
        vehicleMapper.deleteByIdForOrderGood(ids);
        //最终删除
        VehicleExample vehicleExample = new VehicleExample();
        vehicleExample.createCriteria().andIdIn(Arrays.asList(ids));
        int res = vehicleMapper.deleteByExample(vehicleExample);
        if (res != ids.length) {
            throw new DeleteException("删除失败，服务器发生异常。请刷新后重新操作");
        }
    }

    @Override
    public PageVo<Vehicle> selectByQuery(VehicleQueryVo vehicleQueryVo) {
        PageVo<Vehicle> pageVo = new PageVo<>();
        VehicleExample example = new VehicleExample();
        VehicleExample.Criteria criteria = example.createCriteria();
        if (StringUtils.hasLength(vehicleQueryVo.getName())){
            criteria.andNameLike("%"+vehicleQueryVo.getName()+"%");
        }
        if (StringUtils.hasLength(vehicleQueryVo.getAdmin())){
            criteria.andAdminLike("%"+vehicleQueryVo.getAdmin()+"%");
        }
        if (StringUtils.hasLength(vehicleQueryVo.getVehicleNo())){
            criteria.andVehicleNoLike("%"+vehicleQueryVo.getVehicleNo()+"%");
        }
        PageHelper.startPage(vehicleQueryVo.getPageNum(),7);
        List<Vehicle> vehicles = vehicleMapper.selectByExample(example);
        PageInfo<Vehicle> pageInfo = new PageInfo<>(vehicles, 5);
        pageVo.setData(pageInfo.getList());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setPageSize(pageInfo.getPageSize());
        return pageVo;
    }

    @Override
    public List<Vehicle> findByMaxNum(Long maxNum) {
        List<Vehicle> vehicles = vehicleMapper.selectByMaxNum(maxNum);
        return vehicles;
    }

    @Override
    public List<Vehicle> findAll() {
        List<Vehicle> vehicles = vehicleMapper.selectAll();
        return vehicles;
    }

    @Override
    public List<Vehicle> findAll2() {
        List<Vehicle> vehicles = vehicleMapper.selectAll2();
        return vehicles;
    }
}
