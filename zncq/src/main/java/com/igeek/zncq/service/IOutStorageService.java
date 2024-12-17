package com.igeek.zncq.service;

import com.igeek.zncq.entity.OutStorage;
import com.igeek.zncq.vo.*;

import java.util.List;
import java.util.Map;

/**
 * @author liuyi
 */
public interface IOutStorageService {
    /**
     * 查询准备出库订单
     * @param pageNum
     * @return
     */
    PageVo<OutStorageVo> selectAllByPage(Integer pageNum);

    /**
     * 根据订单编号获取订单所有的货物信息
     * @param orderNo
     * @return
     */
    OrderVo findOrderVoByOrderNo(String orderNo);

    /**
     * 根据条件查询
     * @param queryVo
     * @return
     */
    PageVo<OutStorageVo> selectAllByQueryPage(OutStorageQueryVo queryVo);

    /**
     * 查询已经完成出库登记的订单对应的货物
     * @param pageNum
     * @return
     */
    PageVo<OutStorageVo> findFinishOutStoragePage(Integer pageNum);

    /**
     * 出库登记
     * @param outStorageDto
     */
    void outBound(OutStorageDto outStorageDto);

    /**
     * 根据条件查询已经完成出库登记的订单对应的货物
     * @param queryVo
     * @return
     */
    PageVo<OutStorageVo> findFinishOutStoragePageByQuery(OutStorageQueryVo queryVo);

    /**
     * 查找待出库
     * @return
     */
    List<OutStorage> findByNullDate();

    /**
     * 每周定时获取出库周榜前7产品
     * @return
     */
    Map<String, Integer> findPreWeekGoodSumTopSeven();
}
