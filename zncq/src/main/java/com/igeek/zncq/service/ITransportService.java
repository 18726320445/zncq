package com.igeek.zncq.service;

import com.igeek.zncq.vo.*;

import java.util.List;

/**
 * @author liuyi
 */
public interface ITransportService {
    /**
     * 查询对应页码的派车信息
     * @param pageNum
     * @return
     */
    PageVo<TransportVo> selectAllByPage(Integer pageNum);

    PageVo<TransportVo> selectAllByQueryPage(TransportQueryVo queryVo);

    /**
     * 回单
     */
    void receipt(TransportDto transportDto);

    /**
     * 查询所有已经完成的订单
     * @param pageNum
     * @return
     */
    PageVo<OrderVo> selectTOAll(Integer pageNum);

    /**
     * 根据条件查询所有已经完成的订单
     * @param queryVo
     * @return
     */
    PageVo<OrderVo> findTOAllByQuery(TransportQueryVo queryVo);

    /**
     * 根据订单号查询已经登记的货物
     * @param orderNo
     * @return
     */
    List<GoodRawVo> findOrderGoodRegister(String orderNo);

    /**
     * 更新运输车辆所在地
     * @param address
     * @param id
     */
    void updateAddress(String address, int id);

}
