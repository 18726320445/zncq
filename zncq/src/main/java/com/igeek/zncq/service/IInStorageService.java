package com.igeek.zncq.service;

import com.igeek.zncq.entity.InStorage;
import com.igeek.zncq.vo.InStorageQueryVo;
import com.igeek.zncq.vo.InStorageVo;
import com.igeek.zncq.vo.OrderVo;
import com.igeek.zncq.vo.PageVo;

import java.util.List;

/**
 * @author liuyi
 */
public interface IInStorageService {
    /**
     * 查询对应的入库通知并返回
     * @param pageNum
     * @return
     */
    PageVo<InStorageVo> selectInStorageVoByPage(Integer pageNum);

    /**
     * 根据订单号查询
     * @param orderNo
     * @return
     */
    OrderVo selectAllGoodByOrderNo(String orderNo);

    /**
     * 根据条件查询入库情况
     * @param queryVo
     * @return
     */
    PageVo<InStorageVo> selectInStorageVoByQuery(InStorageQueryVo queryVo);

    /**
     * 根据是否有入库时间来查询订单是否入库
     * @param pageNum
     * @return
     */
    PageVo<InStorageVo> selectInStorageVoByInDate(Integer pageNum);

    /**
     * 入库登记
     * @param inStorage
     */
    void register(InStorage inStorage);

    /**
     * 修改入库登记记录
     * @param inStorage
     */
    void updateRegist(InStorage inStorage);

    /**
     * 根据orderNo  goodId查询
     * @param orderNo
     * @param goodId
     * @return
     */
    InStorage selectOneByOrderNoAndGoodId(String orderNo, Integer goodId);

    /**
     * 根据订单号、时间、以及货物名称查询
     * @param queryVo
     * @return
     */
    PageVo<InStorageVo> selectInStorageVoByQuery2(InStorageQueryVo queryVo);

    /**
     * 查看所有待出库
     * @return
     */
    List<InStorage> findByNullDate();
}
