package com.igeek.zncq.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.zncq.entity.*;
import com.igeek.zncq.exception.AddException;
import com.igeek.zncq.exception.DeleteException;
import com.igeek.zncq.exception.UpdateException;
import com.igeek.zncq.mapper.*;
import com.igeek.zncq.service.IPurchaseService;
import com.igeek.zncq.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/20 18:14
 * @email liuyia2022@163.com
 */
@Service
@Transactional(rollbackFor = {})
public class PurchaseServiceImpl implements IPurchaseService {
    @Autowired
    PurchaseMapper purchaseMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    GoodMapper goodMapper;
    @Autowired
    InStorageMapper inStorageMapper;
    @Autowired
    UserMapper userMapper;
    @Override
    public PageVo<PurchaseVo> selectAll(Integer pageNum) {
        PageVo<PurchaseVo> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum,7);
        PurchaseExample purchaseExample = new PurchaseExample();
        purchaseExample.setOrderByClause("createDate desc");
        purchaseExample.createCriteria().andStateEqualTo(0);
        List<Purchase> purchases = purchaseMapper.selectByExample(purchaseExample);
        PageInfo<Purchase> pageInfo = new PageInfo<>(purchases,5);
        List<PurchaseVo> purchaseVos = pageInfo.getList().stream().map(purchase -> {
            PurchaseVo purchaseVo = new PurchaseVo();
            BeanUtils.copyProperties(purchase, purchaseVo);
            Double aDouble = orderMapper.selectTotalByOrderNoDouble(purchase.getOrderNo());
            purchaseVo.setTotal(aDouble);
            return purchaseVo;
        }).collect(Collectors.toList());
        pageVo.setData(purchaseVos);
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        return pageVo;
    }

    @Override
    public void insertPurchase(Purchase purchase) {
        //创建订单
        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());
        order.setOriginatorName(purchase.getCreateName());
        order.setCustomerId(1);
        order.setOrderType("采购订单");
        order.setState(0);
        order.setIsDelete(0);
        order.setCreatedate(new Date());
        int insert = orderMapper.insertOrder(order);
        if (insert != 1){
            throw new AddException("添加异常,订单创建失败");
        }
        purchase.setPurchaseNo(UUID.randomUUID().toString().substring(0,8).toUpperCase());
        purchase.setOrderNo(order.getOrderNo());
        purchase.setCreatedate(order.getCreatedate());
        purchase.setState(0);
        int res = purchaseMapper.insertPurchase(purchase);
        if (res != 1){
            throw new AddException("添加异常,采购订单创建失败");
        }
    }

    @Override
    public PurchaseVo selectPurchaseVoByNo(String purchaseNo) {
        PurchaseVo purchaseVo = purchaseMapper.selectPurchaseVoByNo(purchaseNo);
        if (purchaseVo != null){
            double res = purchaseVo.getGoods().stream().mapToDouble(goodVo -> BigDecimal.valueOf(goodVo.getPrice()).multiply(BigDecimal.valueOf(goodVo.getNum())).doubleValue()).reduce(Double::sum).orElse(-1);
            purchaseVo.setTotal(res);
        }
        return purchaseVo;
    }

    @Override
    public void insertOrderGood(PurchaseDto purchaseDto) {
        //查询出采购对应的订单号
        PurchaseExample purchaseExample = new PurchaseExample();
        purchaseExample.createCriteria().andPurchaseNoEqualTo(purchaseDto.getPurchaseNo()).andStateEqualTo(0);
        List<Purchase> purchases = purchaseMapper.selectByExample(purchaseExample);
        String orderNo = purchases.get(0).getOrderNo();
        int res = orderMapper.insertOrderGood(orderNo, purchaseDto.getGoodId(), purchaseDto.getNum());
        if (res != 1){
            throw new AddException("添加失败,发生未知异常");
        }
        //更新采购订单总数
        Long num = orderMapper.countNumByOrderNoLong(orderNo);
        purchaseExample.clear();
        purchaseExample.createCriteria().andPurchaseNoEqualTo(purchaseDto.getPurchaseNo());
        Purchase purchase = new Purchase();
        purchase.setNum(num);
        int i = purchaseMapper.updateByExampleSelective(purchase, purchaseExample);
        if (i != 1){
            throw new UpdateException("更新数据时发生未知异常，请重新操作");
        }
        PurchaseVo purchaseVo = purchaseMapper.selectPurchaseVoByNo(purchaseDto.getPurchaseNo());
        double total = purchaseVo.getGoods().stream().mapToDouble(goodVo -> BigDecimal.valueOf(goodVo.getPrice()).multiply(BigDecimal.valueOf(goodVo.getNum())).doubleValue()).reduce(Double::sum).orElse(-1.0);
        int i1 = orderMapper.updateNumByOrderNoInt(orderNo, num, total);
        if (i1 != 1){
            throw new UpdateException("更新数据时发生未知异常，请重新操作");
        }
    }

    @Override
    public PurchaseGoodVo selectPurchaseDtoByQuery(PurchaseDto purchaseDto) {
        //查询订单号
        PurchaseExample purchaseExample = new PurchaseExample();
        purchaseExample.createCriteria().andPurchaseNoEqualTo(purchaseDto.getPurchaseNo());
        List<Purchase> purchases = purchaseMapper.selectByExample(purchaseExample);
        if (purchases == null){
            throw new UpdateException("不存在该采购订单,请刷新");
        }
        String orderNo = purchases.get(0).getOrderNo();
        Integer num = purchaseMapper.selectNumOrderGood(orderNo, purchaseDto.getGoodId());
        if (num == null){
            throw new RuntimeException("该订单不存在该货物");
        }
        purchaseDto.setNum(Long.valueOf(num));
        Good good = goodMapper.selectByPrimaryKey(purchaseDto.getGoodId());
        PurchaseGoodVo purchaseGoodVo = new PurchaseGoodVo();
        BeanUtils.copyProperties(purchaseDto,purchaseGoodVo);
        purchaseGoodVo.setGoodName(good.getGoodName());
        return purchaseGoodVo;
    }

    @Override
    public void updatePurchaseGoodByQuery(PurchaseDto purchaseDto) {
        PurchaseExample purchaseExample = new PurchaseExample();
        purchaseExample.createCriteria().andPurchaseNoEqualTo(purchaseDto.getPurchaseNo());
        List<Purchase> purchases = purchaseMapper.selectByExample(purchaseExample);
        if (purchases == null){
            throw new UpdateException("不存在该采购订单,请刷新");
        }
        String orderNo = purchases.get(0).getOrderNo();
        int res = purchaseMapper.updateNumOrderGood(orderNo, purchaseDto.getGoodId(), purchaseDto.getNum());
        if (res != 1){
            throw new UpdateException("修改失败，该采购订单的该物品不存在");
        }
        //更新总数
        PurchaseVo purchaseVo = purchaseMapper.selectPurchaseVoByNo(purchaseDto.getPurchaseNo());
        List<GoodRawVo> goods = purchaseVo.getGoods();
        long num = goods.stream().mapToLong(GoodRawVo::getNum).reduce((a, b) -> a + b).orElse(-1);
        Purchase purchase = new Purchase();
        purchase.setPurchaseNo(purchaseDto.getPurchaseNo());
        purchase.setNum(num);
        purchaseExample.clear();
        purchaseExample.createCriteria().andPurchaseNoEqualTo(purchase.getPurchaseNo());
        int ans = purchaseMapper.updateByExampleSelective(purchase, purchaseExample);
        if (ans != 1){
            throw new UpdateException("更新数据时出错，请重新操作");
        }
        //order订单更新
        double total = goods.stream().mapToDouble(goodVo -> BigDecimal.valueOf(goodVo.getPrice()).multiply(BigDecimal.valueOf(goodVo.getNum())).doubleValue()).reduce(Double::sum).orElse(-1.0);
        int i1 = orderMapper.updateNumByOrderNoInt(orderNo, num, total);
        if (i1 != 1){
            throw new UpdateException("更新数据时发生未知异常，请重新操作");
        }
    }

    @Override
    public void deletePurchaseGoodByQuery(PurchaseDto purchaseDto) {
        if (purchaseDto == null){
            throw new RuntimeException("请传入参数");
        }
        if(purchaseDto.getGoodId() == null || !StringUtils.hasLength(purchaseDto.getPurchaseNo())){
            throw new RuntimeException("参数不全");
        }
        Purchase purchase = purchaseMapper.selectOneByPurchaseNo(purchaseDto.getPurchaseNo());
        if (purchase == null){
            throw new DeleteException("该采购订单不存在,请刷新");
        }
        int res = purchaseMapper.deleteOrderGoodByQuery(purchase.getOrderNo(), purchaseDto.getGoodId());
        if (res != 1){
            throw new DeleteException("删除失败,该采购订单的该物品已不存在");
        }
        //更新总数
        Long num = orderMapper.countNumByOrderNoLong(purchase.getOrderNo());
        purchase.setNum(num);
        PurchaseExample purchaseExample = new PurchaseExample();
        purchaseExample.createCriteria().andPurchaseNoEqualTo(purchase.getPurchaseNo());
        int ans = purchaseMapper.updateByExampleSelective(purchase, purchaseExample);
        if (ans != 1){
            throw new UpdateException("更新数据时出错,请重新操作");
        }
        PurchaseVo purchaseVo = purchaseMapper.selectPurchaseVoByNo(purchaseDto.getPurchaseNo());
        double total = purchaseVo.getGoods().stream().mapToDouble(goodVo -> BigDecimal.valueOf(goodVo.getPrice()).multiply(BigDecimal.valueOf(goodVo.getNum())).doubleValue()).reduce(Double::sum).orElse(-1.0);
        int i1 = orderMapper.updateNumByOrderNoInt(purchase.getOrderNo(), num, total);
        if (i1 != 1){
            throw new UpdateException("更新数据时发生未知异常，请重新操作");
        }
    }

    @Override
    public void updateState(String purchaseNo) {
        Purchase purchase = purchaseMapper.selectOneByPurchaseNo(purchaseNo);
        if (purchase == null){
            throw new UpdateException("该订单不存在!");
        }
        int res = purchaseMapper.updateState(purchaseNo,1);
        if (res != 1){
            throw new UpdateException("更新时发生错误,请重新操作");
        }

        int i = orderMapper.updateStateByOrderNoInt(purchase.getOrderNo(), 1);
        if (i != 1){
            throw new UpdateException("更新时发生错误,请重新操作");
        }
    }

    @Override
    public PageVo<PurchaseVo> findAllByQuery(PurchaseDto purchaseDto) {
        PageVo<PurchaseVo> pageVo = new PageVo<>();
        PurchaseExample purchaseExample = new PurchaseExample();
        PurchaseExample.Criteria criteria = purchaseExample.createCriteria();
        criteria.andStateEqualTo(0);

        if (StringUtils.hasLength(purchaseDto.getPurchaseNo())){
            criteria.andPurchaseNoLike("%"+purchaseDto.getPurchaseNo()+"%");
        }
        if (purchaseDto.getFromDate() != null && purchaseDto.getToDate() != null){
            criteria.andCreatedateBetween(purchaseDto.getFromDate(),purchaseDto.getToDate());
        }

        PageHelper.startPage(purchaseDto.getPageNum(),7);

        List<Purchase> purchases = purchaseMapper.selectByExample(purchaseExample);
        PageInfo<Purchase> pageInfo = new PageInfo<>(purchases, 5);
        List<PurchaseVo> purchaseVos = pageInfo.getList().stream().map(purchase -> {
            PurchaseVo purchaseVo = new PurchaseVo();
            BeanUtils.copyProperties(purchase, purchaseVo);
            Double aDouble = orderMapper.selectTotalByOrderNoDouble(purchase.getOrderNo());
            purchaseVo.setTotal(aDouble);
            return purchaseVo;
        }).collect(Collectors.toList());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(purchaseVos);
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        return pageVo;
    }

    @Override
    public PageVo<PurchaseVo> findAllByState(Integer pageNum, Integer state) {
        PageVo<PurchaseVo> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum,7);

        PurchaseExample purchaseExample = new PurchaseExample();
        purchaseExample.createCriteria().andStateIn(Arrays.asList(new Integer[]{-1,1,2}));
        List<Purchase> purchases = purchaseMapper.selectByExample(purchaseExample);

        PageInfo<Purchase> pageInfo = new PageInfo<>(purchases,5);

        List<PurchaseVo> purchaseVos = pageInfo.getList().stream().map(purchase -> {
            PurchaseVo purchaseVo = new PurchaseVo();
            BeanUtils.copyProperties(purchase, purchaseVo);
            Double aDouble = orderMapper.selectTotalByOrderNoDouble(purchase.getOrderNo());
            purchaseVo.setTotal(aDouble);
            return purchaseVo;
        }).collect(Collectors.toList());

        pageVo.setData(purchaseVos);
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        return pageVo;
    }

    @Override
    public PageVo<PurchaseVo> selectAllByQueryAndState(PurchaseDto purchaseDto) {
        PageVo<PurchaseVo> pageVo = new PageVo<>();
        PurchaseExample purchaseExample = new PurchaseExample();
        PurchaseExample.Criteria criteria = purchaseExample.createCriteria();
        criteria.andStateIn(Arrays.asList(new Integer[]{-1,1,2}));
        if (StringUtils.hasLength(purchaseDto.getPurchaseNo())){
            criteria.andPurchaseNoLike("%"+purchaseDto.getPurchaseNo()+"%");
        }
        if (purchaseDto.getFromDate() != null && purchaseDto.getToDate() != null){
            criteria.andCreatedateBetween(purchaseDto.getFromDate(),purchaseDto.getToDate());
        }
        PageHelper.startPage(purchaseDto.getPageNum(),7);
        List<Purchase> purchases = purchaseMapper.selectByExample(purchaseExample);
        PageInfo<Purchase> pageInfo = new PageInfo<>(purchases, 5);
        List<PurchaseVo> purchaseVos = pageInfo.getList().stream().map(purchase -> {
            PurchaseVo purchaseVo = new PurchaseVo();
            BeanUtils.copyProperties(purchase, purchaseVo);
            Double aDouble = orderMapper.selectTotalByOrderNoDouble(purchase.getOrderNo());
            purchaseVo.setTotal(aDouble);
            return purchaseVo;
        }).collect(Collectors.toList());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(purchaseVos);
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        return pageVo;
    }

    @Override
    public void agreeOrder(String purchaseNo,String processName,Integer warehouseId) {
        //加入审核人
        Purchase purchase = purchaseMapper.selectOneByPurchaseNo(purchaseNo);
        if (purchase == null){
            throw new RuntimeException("该订单不存在，请刷新页面");
        }
        if (purchase.getState() != 1){
            throw new UpdateException("该采购订单已经处理过,无法重复处理");
        }
        purchase.setState(2);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(processName);
        User user = userMapper.selectByExample(userExample).get(0);
        purchase.setProcessName(user.getNickName());
        PurchaseExample purchaseExample = new PurchaseExample();
        purchaseExample.createCriteria().andPurchaseNoEqualTo(purchaseNo);
        int res = purchaseMapper.updateByExampleSelective(purchase, purchaseExample);
        if (res != 1){
            throw  new UpdateException("操作失败，请重新操作");
        }
        //更新order状态
        int i = orderMapper.updateStateAndProcessNameByOrderNoInt(purchase.getOrderNo(),2,processName);
        if (i != 1){
            throw new UpdateException("操作失败，请重新操作");
        }
        //加入入库通知
        PurchaseVo purchaseVo = purchaseMapper.selectPurchaseVoByNo(purchaseNo);
        purchaseVo.getGoods().stream().forEach(goodRawVo -> {
            InStorage inStorage = new InStorage();
            inStorage.setWarehouseId(warehouseId);
            inStorage.setGoodId(goodRawVo.getGoodId());
            inStorage.setOrderNo(purchase.getOrderNo());
            inStorage.setNum(goodRawVo.getNum());
            int i1 = inStorageMapper.insertSelective(inStorage);
            if (i1 != 1){
                throw new UpdateException("操作失败，请重新操作");
            }
        });
    }
    @Override
    public void passOrderAndDelete(String purchaseNo) {
        //找到对应的订单
        Purchase purchase = purchaseMapper.selectOneByPurchaseNo(purchaseNo);
        if (purchase == null){
            throw new RuntimeException("该订单不存在，请刷新页面");
        }

        purchaseMapper.updateState(purchaseNo,-1);
        //添加处理人
        PurchaseExample purchaseExample = new PurchaseExample();
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        purchase.setProcessName(username);
        purchaseMapper.updateProcessName(purchase);
        //删除对应的货物记录
//        orderMapper.deleteByOrderNoInt(purchase.getOrderNo());
        //更新order状态
        int i = orderMapper.updateStateByOrderNoInt(purchase.getOrderNo(), -1);
        if (i != 1){
            throw new UpdateException("操作失败，请重新操作");
        }
        int i1 = orderMapper.updateIsDeleteByOrderNo(purchase.getOrderNo());
        if (i1 != 1){
            throw new UpdateException("操作失败，请重新操作");
        }

    }

    @Override
    public Integer countByState(int i) {
        PurchaseExample purchaseExample = new PurchaseExample();
        purchaseExample.createCriteria().andStateEqualTo(i);
        return purchaseMapper.countByExample(purchaseExample);
    }
}
