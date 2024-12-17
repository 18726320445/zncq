package com.igeek.zncq.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.zncq.entity.*;
import com.igeek.zncq.exception.AddException;
import com.igeek.zncq.exception.DeleteException;
import com.igeek.zncq.exception.UpdateException;
import com.igeek.zncq.mapper.*;
import com.igeek.zncq.service.IContractService;
import com.igeek.zncq.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/25 13:28
 * @email liuyia2022@163.com
 */
@Service
@Transactional(rollbackFor = {})
public class ContractServiceImpl implements IContractService {
    @Autowired
    ContractMapper contractMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OutStorageMapper outStorageMapper;
    @Autowired
    TransportMapper transportMapper;
    @Autowired
    VehicleMapper vehicleMapper;
    @Autowired
    InStorageMapper inStorageMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    EmailService emailService;
    @Override
    public void insertContract(Contract contract) {
        //生成对应的订单
        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());
        order.setOriginatorName(contract.getCreateName());
        order.setCustomerId(contract.getCustomerId());
        order.setOrderType("合约订单");
        order.setState(0);
        order.setIsDelete(0);
        order.setCreatedate(new Date());
        int res = orderMapper.insertOrder(order);
        if (res != 1){
            throw new AddException("创建失败，服务器异常请重新操作");
        }
        //创建合同
        contract.setContractNo(UUID.randomUUID().toString().substring(0,8).toUpperCase());
        contract.setCreatedate(order.getCreatedate());
        contract.setOrderNo(order.getOrderNo());
        int i = contractMapper.insertSelective(contract);
        if (i != 1){
            throw new AddException("创建失败，服务器异常请重新操作");
        }
    }

    @Override
    public PageVo<ContractVo> selectAllByPage(Integer pageNum) {
        PageVo<ContractVo> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum,7);
        List<ContractVo> contractVos = contractMapper.selectContractVoAllByPage();
        PageInfo<ContractVo> pageInfo = new PageInfo<>(contractVos, 5);
        pageVo.setData(pageInfo.getList());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        return pageVo;
    }

    @Override
    public ContractVo selectContractVo(String contractNo) {
        if (!StringUtils.hasLength(contractNo)){
            throw new RuntimeException("请选择合同编号");
        }
        ContractVo contractVo = contractMapper.selectContractVo(contractNo);
        return contractVo;
    }

    @Override
    public void insertGoodByContractNo(ContractDto contractDto) {
        ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andContractNoEqualTo(contractDto.getContractNo()).andStateEqualTo(0);
        List<Contract> contracts = contractMapper.selectByExample(contractExample);
        if (contracts == null){
         throw new AddException("该合约不存在请刷新页面");
        }
        Contract contract = contracts.get(0);
        //更新关系表
        String orderNo = contract.getOrderNo();
        int res = orderMapper.insertOrderGood(orderNo, contractDto.getGoodId(), contractDto.getNum());
        if (res != 1){
            throw new AddException("添加失败，服务器异常请重新操作");
        }
        //跟新合同表以及订单表的总数、总金额
        ContractVo contractVo = contractMapper.selectContractVo(contract.getContractNo());
        List<GoodRawVo> goods = contractVo.getGoods();
        Long num = goods.stream().map(GoodRawVo::getNum).reduce((aLong, aLong2) -> aLong + aLong2).orElse(null);
        Double total = 0.0;
        for (GoodRawVo good : goods) {
            total += new BigDecimal(good.getNum()).multiply(new BigDecimal(good.getPrice())).doubleValue();
        }
        int i = orderMapper.updateNumByOrderNoInt(orderNo, num, total);
        if (i != 1){
            throw new UpdateException("更新订单失败,请重新操作");
        }
    }

    @Override
    public void updateGoodByContractNo(ContractDto contractDto) {
        ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andContractNoEqualTo(contractDto.getContractNo()).andStateEqualTo(0);
        List<Contract> contracts = contractMapper.selectByExample(contractExample);
        if (contracts == null){
            throw new AddException("该合约不存在请刷新页面");
        }
        Contract contract = contracts.get(0);
        //更新关系表
        String orderNo = contract.getOrderNo();
        Integer res = contractMapper.updateGoodNum(orderNo,contractDto.getGoodId(),contractDto.getNum());
        if (res != 1){
            throw new UpdateException("更新失败，服务器异常请重新操作");
        }
        //跟新合同表以及订单表的总数、总金额
        ContractVo contractVo = contractMapper.selectContractVo(contract.getContractNo());
        List<GoodRawVo> goods = contractVo.getGoods();
        Long num = goods.stream().map(GoodRawVo::getNum).reduce((aLong, aLong2) -> aLong + aLong2).orElse(null);
        double total = goods.stream().mapToDouble(goodVo -> BigDecimal.valueOf(goodVo.getPrice()).multiply(BigDecimal.valueOf(goodVo.getNum())).doubleValue()).reduce(Double::sum).orElse(-1.0);
        int i = orderMapper.updateNumByOrderNoInt(orderNo, num, total);
        if (i != 1){
            throw new UpdateException("更新订单失败,请重新操作");
        }
    }

    @Override
    public void deleteGoodByContractNo(ContractDto contractDto) {
        ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andContractNoEqualTo(contractDto.getContractNo()).andStateEqualTo(0);
        List<Contract> contracts = contractMapper.selectByExample(contractExample);
        if (contracts == null){
            throw new AddException("该合约不存在请刷新页面");
        }
        Contract contract = contracts.get(0);
        //更新关系表
        String orderNo = contract.getOrderNo();
        Integer res = contractMapper.deleteGoodById(orderNo,contractDto.getGoodId());
        if (res != 1){
            throw new UpdateException("删除失败，服务器异常请重新操作");
        }
        //跟新合同表以及订单表的总数、总金额
        ContractVo contractVo = contractMapper.selectContractVo(contract.getContractNo());
        List<GoodRawVo> goods = contractVo.getGoods();
        Long num = goods.stream().map(GoodRawVo::getNum).reduce((aLong, aLong2) -> aLong + aLong2).orElse(null);
        double total = goods.stream().mapToDouble(goodVo -> BigDecimal.valueOf(goodVo.getPrice()).multiply(BigDecimal.valueOf(goodVo.getNum())).doubleValue()).reduce(Double::sum).orElse(-1.0);
        int i = orderMapper.updateNumByOrderNoInt(orderNo, num, total);
        if (i != 1){
            throw new UpdateException("更新订单失败,请重新操作");
        }
    }

    @Override
    public PageVo<ContractVo> selectAllByQueryPage(ContractDto contractDto) {
        PageVo<ContractVo> pageVo = new PageVo<>();
        PageHelper.startPage(contractDto.getPageNum(),7);
        List<ContractVo> contractVos = contractMapper.selectContractVoAllByQueryPage(contractDto);
        PageInfo<ContractVo> pageInfo = new PageInfo<>(contractVos, 5);
        pageVo.setData(pageInfo.getList());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        return pageVo;
    }

    @Override
    public void commitContractByContractNo(String contractNo) {
        //确定合约是否存在
        ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andContractNoEqualTo(contractNo);
        List<Contract> contracts = contractMapper.selectByExample(contractExample);
        if (contracts == null){
            throw new RuntimeException("该合约不存在请刷新页面");
        }
        Contract contract = contracts.get(0);
        //更新
        contract.setState(1);
        int i = contractMapper.updateByExampleSelective(contract,contractExample);
        if (i != 1){
            throw new UpdateException("更新异常，请重新操作");
        }
        //订单更新
        int res = orderMapper.updateStateByOrderNoInt(contract.getOrderNo(), 1);
        if (res != 1){
            throw new UpdateException("更新异常，请重新操作");
        }
    }

    @Override
    public PageVo<ContractVo> selectContractVoByStatePage(Integer pageNum) {
        PageVo<ContractVo> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum,7);
        List<ContractVo> contractVos = contractMapper.selectContractVoByStates(new Integer[]{1,-1,2});
        PageInfo<ContractVo> pageInfo = new PageInfo<>(contractVos, 5);
        pageVo.setData(pageInfo.getList());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        return pageVo;
    }

    @Override
    public void goodAgree(String contractNo,Integer vehicleId ,String processName) {
        //查看该合约是否存在
        ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andContractNoEqualTo(contractNo);
        List<Contract> contracts = contractMapper.selectByExample(contractExample);
        if (contracts == null){
            throw new UpdateException("该合约不存在");
        }
        Contract contract = contracts.get(0);
        if (contract.getState() != 1){
            throw new UpdateException("该合约已经处理过,无法重复处理");
        }
        //更新状态
        contract.setState(2);
        contract.setSigndate(new Date());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(processName);
        User user = userMapper.selectByExample(userExample).get(0);
        int i = contractMapper.updateByExampleSelective(contract, contractExample);
        if (i != 1){
            throw new UpdateException("更新异常，请重新操作");
        }
        int i1 = orderMapper.updateStateAndProcessNameByOrderNoInt(contract.getOrderNo(), 2,user.getNickName());
        if (i1 != 1){
            throw new UpdateException("更新异常，请重新操作");
        }
        //更新关系表
        orderMapper.updateVehicleByOrderNo(contract.getOrderNo(),vehicleId);
        //出库通知
        ContractVo contractVo = contractMapper.selectContractVo(contractNo);
        contractVo.getGoods().stream().forEach(goodRawVo -> {
            OutStorage outStorage = new OutStorage();
            outStorage.setGoodId(goodRawVo.getGoodId());
            outStorage.setOrderNo(contract.getOrderNo());
            outStorage.setNum(goodRawVo.getNum());
            int i2 = outStorageMapper.insertSelective(outStorage);
            if (i2 != 1){
                throw new AddException("出库通知生成异常,请重新操作");
            }
        });
        //更新车辆状态
        int i3 = vehicleMapper.updateVehicleState(vehicleId, 1);
        if (i3 != 1){
            throw new UpdateException("更新异常，请重新操作");
        }
        //生成运单
        Transport transport = new Transport();
        transport.setState(0);
        transport.setVehicleId(vehicleId);
        int i2 = transportMapper.insertSelective(transport);
        if (i2 != 1){
            throw new AddException("运单生成异常,请重新操作");
        }
    }

    @Override
    public void equipAgree(String contractNo,String processName) {
        //查看该合约是否存在
        ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andContractNoEqualTo(contractNo);
        List<Contract> contracts = contractMapper.selectByExample(contractExample);
        if (contracts == null){
            throw new UpdateException("该合约不存在");
        }
        Contract contract = contracts.get(0);
        if (contract.getState() != 1){
            throw new UpdateException("该合约已经处理过,无法重复处理");
        }
        //更新状态
        contract.setState(2);
        contract.setSigndate(new Date());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(processName);
        User user = userMapper.selectByExample(userExample).get(0);
        int i = contractMapper.updateByExampleSelective(contract, contractExample);
        if (i != 1){
            throw new UpdateException("更新异常，请重新操作");
        }
        int i1 = orderMapper.updateStateAndProcessNameByOrderNoInt(contract.getOrderNo(), 2,user.getNickName());
        if (i1 != 1){
            throw new UpdateException("更新异常，请重新操作");
        }
        //入库通知
        ContractVo contractVo = contractMapper.selectContractVo(contractNo);
        contractVo.getGoods().stream().forEach(goodRawVo -> {
            InStorage inStorage = new InStorage();
            inStorage.setOrderNo(contract.getOrderNo());
            inStorage.setGoodId(goodRawVo.getGoodId());
            inStorage.setNum(goodRawVo.getNum());
            int i2 = inStorageMapper.insertSelective(inStorage);
            if (i2 != 1){
                throw new AddException("入库通知生成异常,请重新操作");
            }
        });
    }

    @Override
    public void passContract(String contractNo,String processName) {
        ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andContractNoEqualTo(contractNo);
        List<Contract> contracts = contractMapper.selectByExample(contractExample);
        if (contracts == null){
            throw new UpdateException("该合约不存在");
        }
        Contract contract = contracts.get(0);
        if (contract.getState() != 1){
            throw new UpdateException("该合约已经处理过,无法重复处理");
        }
        //更新状态
        contract.setState(-1);
        contract.setSigndate(new Date());
        int i = contractMapper.updateByExampleSelective(contract, contractExample);
        if (i != 1){
            throw new UpdateException("更新异常，请重新操作");
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(processName);
        User user = userMapper.selectByExample(userExample).get(0);
        //将订单逻辑删除
        int i2 = orderMapper.updateStateAndProcessNameByOrderNoInt(contract.getOrderNo(),-1,user.getNickName());
        if (i2 != 1){
            throw new DeleteException("删除异常,请重新操作");
        }
        int i1 = orderMapper.updateIsDeleteByOrderNo(contract.getOrderNo());
        if (i1 != 1){
            throw new DeleteException("删除异常,请重新操作");
        }

        //将关系表中的记录删除
//        orderMapper.deleteByOrderNoInt(contract.getOrderNo());
        //发邮件提示
        String email = user.getEmail();
        emailService.sendMessage(email,"订单审核结果","你的提交的合约:\n" +"" +
                "合约名称：\n" + contract.getContractName() + "合约类型:\n" + contract.getContractType()
                    + "合约号:\n" + contract.getContractNo() +"\n审核未通过");

    }

    @Override
    public PageVo<ContractVo> selectContractVoByStatePageQuery(ContractQueryVo queryVo) {
        PageVo<ContractVo> pageVo = new PageVo<>();
        PageHelper.startPage(queryVo.getPageNum(),7);
        List<ContractVo> contractVos = contractMapper.selectContractVoByStatePageQuery(queryVo,new Integer[]{1,-1,2});
        PageInfo<ContractVo> pageInfo = new PageInfo<>(contractVos, 5);
        pageVo.setData(pageInfo.getList());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        return pageVo;
    }

    @Override
    public Integer countByState(int i) {
        ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andStateEqualTo(i);
        return contractMapper.countByExample(contractExample);
    }

}
