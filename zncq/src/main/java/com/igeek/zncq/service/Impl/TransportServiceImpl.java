package com.igeek.zncq.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.zncq.entity.*;
import com.igeek.zncq.exception.UpdateException;
import com.igeek.zncq.mapper.*;
import com.igeek.zncq.service.ITransportService;
import com.igeek.zncq.vo.*;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/1 09:48
 * @email liuyia2022@163.com
 */
@Service
@Transactional(rollbackFor = {})
public class TransportServiceImpl implements ITransportService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    TransportMapper transportMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    InStorageMapper inStorageMapper;
    @Autowired
    OutStorageMapper outStorageMapper;
    @Autowired
    WarehouseMapper warehouseMapper;
    @Autowired
    VehicleMapper vehicleMapper;
    @Autowired
    EmailService emailService;
    @Autowired
    ContractMapper contractMapper;
    @Autowired
    JedisPool jedisPool;
    @Autowired
    TransportContext transportContext;
    @Autowired
    TranferStrategy tranferStrategy;

    @Override
    public PageVo<TransportVo> selectAllByPage(Integer pageNum) {
        PageVo<TransportVo> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum, 7);
        List<TransportVo> transportVos = transportMapper.selectAllByGtState(1);
        PageInfo<TransportVo> pageInfo = new PageInfo<>(transportVos, 5);
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(pageInfo.getList());
        pageVo.setPageSize(pageInfo.getPageSize());
        return pageVo;
    }

    @Override
    public PageVo<TransportVo> selectAllByQueryPage(TransportQueryVo queryVo) {
        if (queryVo.getState() == null) {
            queryVo.setState(1);
        }
        PageVo<TransportVo> pageVo = new PageVo<>();
        PageHelper.startPage(queryVo.getPageNum(), 7);
        List<TransportVo> transportVos = transportMapper.selectAllByGtStateQuery(queryVo);
        PageInfo<TransportVo> pageInfo = new PageInfo<>(transportVos, 5);
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(pageInfo.getList());
        pageVo.setPageSize(pageInfo.getPageSize());
        return pageVo;
    }

    @Override
    public void receipt(TransportDto transportDto) {
        //查看运输车辆是否相同
        Integer goodId = transportDto.getGoodId();
        String orderNo = transportDto.getOrderNo();

        OrderGoodVo orderGoodVo = orderMapper.selectOrderGood(orderNo, goodId);
        if (orderGoodVo == null) {
            throw new UpdateException("订单不存在或该货物不在本订单内，请核对");
        }
        if (!orderGoodVo.getVehicleId().equals(transportDto.getVehicleId())) {
            throw new UpdateException("运输订单车辆不一致，请核对");
        }
        //判断是否已经回单防止重复回单或提前回单
        OrderVo orderVo = orderMapper.selectOneByOrderNo(orderNo);

        TransportExample transportExample = new TransportExample();
        transportExample.createCriteria().andVehicleIdEqualTo(transportDto.getVehicleId()).andStateEqualTo(1);
        List<Transport> transports = transportMapper.selectByExample(transportExample);

        //transport!=null  代表订单在运输中
        if (transports == null || transports.size() == 0) {
            throw new UpdateException("该订单的该货物已经被处理，不允许重复处理");
        }
        Transport transport = transports.get(0);
        //回单
        Long oldNum = null;
        String orderType = orderVo.getOrderType();
        if ("移库订单".equals(orderType)) {
            InStorageExample inStorageExample = new InStorageExample();
            inStorageExample.createCriteria().andIndateIsNotNull().andOrderNoEqualTo(transportDto.getOrderNo());
            List<InStorage> inStorages = inStorageMapper.selectByExample(inStorageExample);
            if (inStorages == null || inStorages.size() == 0){
                throw new UpdateException("订单还未入库无法回单");
            }
            //更改入库通知数量
            transportContext.setTransportDto(transportDto);
            transportContext.setAbstractTransportStrategy(tranferStrategy);
            InStorage inStorage = (InStorage) transportContext.execute();
            oldNum = inStorage.getNum();
        } else if ("合约订单".equals(orderType)) {
            //检查该订单是否出库，是否入库后才会回单否则打回
            OutStorageExample outStorageExample = new OutStorageExample();
            outStorageExample.createCriteria().andOutdateIsNotNull().andOrderNoEqualTo(transportDto.getOrderNo());
            List<OutStorage> outStorages = outStorageMapper.selectByExample(outStorageExample);
            if (outStorages == null){
                throw new UpdateException("订单还未出库无法回单");
            }
            //改变订单状态
            int i = orderMapper.updateStateByOrderNoInt(orderNo, 3);
            if (i != 1) {
                throw new UpdateException("回单异常,重新操作");
            }
        }
        //改变运单状态以及车辆状态
        transport.setMidAddress(transport.getEndAddress());
        transport.setState(2);
        int i = transportMapper.updateByExample(transport, transportExample);
        if (i != 1) {
            throw new UpdateException("回单异常,重新操作");
        }
        int i1 = vehicleMapper.updateVehicleState(transportDto.getVehicleId(), 0);
        if (i1 != 1) {
            throw new UpdateException("回单异常,重新操作");
        }
        //将该货物登记状态改为已登记
        int i2 = orderMapper.updateStateOrederGood(orderNo, 1, goodId);
        if (i2 != 1) {
            throw new UpdateException("回单异常,重新操作");
        }
        String processName = orderVo.getProcessName();
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(processName);
        List<User> users = userMapper.selectByExample(userExample);
        User user = users.get(0);
        //如果回单数和预期值不同则邮件通知审核人员
        if (!oldNum.equals(transportDto.getNum())) {
            String content = "订单交付时，交付数量与预计不符合,请及时与客户联系并处理.订单号为:" + orderNo;
            emailService.sendMessage(user.getEmail(),"回单异常",content);
        }
    }

    @Override
    public PageVo<OrderVo> selectTOAll(Integer pageNum) {
        PageVo<OrderVo> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum, 7);
        List<OrderVo> orderVos = orderMapper.selectOrderVoAll(null, null);
        PageInfo<OrderVo> pageInfo = new PageInfo<>(orderVos, 5);
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(pageInfo.getList());
        pageVo.setPageSize(pageInfo.getPageSize());
        return pageVo;
    }

    @Override
    public PageVo<OrderVo> findTOAllByQuery(TransportQueryVo queryVo) {
        PageVo<OrderVo> pageVo = new PageVo<>();
        PageHelper.startPage(queryVo.getPageNum(), 7);
        List<OrderVo> orderVos = orderMapper.selectOrderVoAll(queryVo.getOrderNo(), queryVo.getVehicleNo());
        PageInfo<OrderVo> pageInfo = new PageInfo<>(orderVos, 5);
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(pageInfo.getList());
        pageVo.setPageSize(pageInfo.getPageSize());
        return pageVo;
    }

    @Override
    public List<GoodRawVo> findOrderGoodRegister(String orderNo) {
        List<GoodRawVo> goodRawVos = null;
        OrderVo orderVo = orderMapper.selectOneByOrderNo(orderNo);
        if ("合约订单".equals(orderVo.getOrderType())) {
            ContractExample contractExample = new ContractExample();
            contractExample.createCriteria().andOrderNoEqualTo(orderNo);
            List<Contract> contracts = contractMapper.selectByExample(contractExample);
            Contract contract = contracts.get(0);
            if (contract.getContractType().equals(0)) {

                goodRawVos = inStorageMapper.selectGoodVoByInStorageOrderNo(orderNo);
            } else {
                goodRawVos = inStorageMapper.selectGoodVoByOrderNo(orderNo);
            }
        } else {
            goodRawVos = inStorageMapper.selectGoodVoByInStorageOrderNo(orderNo);
        }
        return goodRawVos;
    }

    @Override
    public void updateAddress(String address, int id) {
        String midAddressKey = "transport_"+id +"_midAddress";
        String value = address;
        Jedis resource = jedisPool.getResource();
        String set = resource.set(midAddressKey, value);
        if (!"OK".equals(set)){
            throw new UpdateException("更新异常,请重新操作");
        }
    }

}
