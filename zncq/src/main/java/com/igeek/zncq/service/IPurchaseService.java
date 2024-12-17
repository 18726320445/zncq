package com.igeek.zncq.service;

import com.igeek.zncq.entity.Purchase;
import com.igeek.zncq.vo.PageVo;
import com.igeek.zncq.vo.PurchaseDto;
import com.igeek.zncq.vo.PurchaseGoodVo;
import com.igeek.zncq.vo.PurchaseVo;


/**
 *
 * @author liuyi
 */

public interface IPurchaseService {
    /**
     * 根据页码查询所有未确定的采购订单
     * @param pageNum
     * @return
     */
    PageVo<PurchaseVo> selectAll(Integer pageNum);

    /**
     * 添加采购订单
     * @param purchase
     */
    void insertPurchase(Purchase purchase);

    /**
     * 根据采购单号查询对应的货物信息
     * @param purchaseNo
     * @return
     */
    PurchaseVo selectPurchaseVoByNo(String purchaseNo);

    /**
     * 给采购订单添加货物
     * @param purchaseDto
     */
    void insertOrderGood(PurchaseDto purchaseDto);

    /**
     * 根据goodid和purchase查询 数量
     * @param purchaseDto
     * @return
     */
    PurchaseGoodVo selectPurchaseDtoByQuery(PurchaseDto purchaseDto);

    /**
     * 根据goodid和purchase更新 数量
     * @param purchaseDto
     */
    void updatePurchaseGoodByQuery(PurchaseDto purchaseDto);

    /**
     * 根据goodid和purchaseNo删除
     * @param purchaseDto
     */
    void deletePurchaseGoodByQuery(PurchaseDto purchaseDto);

    /**
     * 根据purchaseNo改变订单状态
     * @param purchase
     */
    void updateState(String purchase);

    /**
     * 根据给的条件查询采购订单信息
     * @param purchaseDto
     * @return
     */
    PageVo<PurchaseVo> findAllByQuery(PurchaseDto purchaseDto);

    /**
     * 根据状态查询
     * @param pageNum
     * @param state
     * @return
     */
    PageVo<PurchaseVo> findAllByState(Integer pageNum,Integer state);

    /**
     * 根据条件查询对应的已经确定的采购订购记录
     * @param purchaseDto
     * @return
     */
    PageVo<PurchaseVo> selectAllByQueryAndState(PurchaseDto purchaseDto);

    /**
     * 采购订单同意
     * @param purchaseNo
     */
    void agreeOrder(String purchaseNo,String processName,Integer warehouseId);

    /**
     * 取消采购订单
     * @param purchaseNo
     */
    void passOrderAndDelete(String purchaseNo);

    /**
     * 根据状态统计订单数
     * @param i
     * @return
     */
    Integer countByState(int i);
}
