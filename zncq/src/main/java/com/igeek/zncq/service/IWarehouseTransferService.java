package com.igeek.zncq.service;

import com.igeek.zncq.vo.PageVo;
import com.igeek.zncq.vo.WarehouseTransferDto;
import com.igeek.zncq.vo.WarehouseTransferQueryVo;
import com.igeek.zncq.vo.WarehouseTransferVo;

/**
 * @author liuyi
 */
public interface IWarehouseTransferService {
    /**
     * 移库操作
     * @param warehouseTransferDto
     */
    void transfer(WarehouseTransferDto warehouseTransferDto);

    /**
     * 根据页码查询移库信息
     * @param pageNum
     * @return
     */
    PageVo<WarehouseTransferVo> findAll(Integer pageNum);

    /**
     * 根据条件查询
     * @param queryVo
     * @return
     */
    PageVo<WarehouseTransferVo> findAllByQuery(WarehouseTransferQueryVo queryVo);
}
