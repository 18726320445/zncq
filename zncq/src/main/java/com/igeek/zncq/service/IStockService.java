package com.igeek.zncq.service;

import com.igeek.zncq.entity.ProduceConsume;
import com.igeek.zncq.vo.PageVo;
import com.igeek.zncq.vo.StockQueryVo;
import com.igeek.zncq.vo.StockVo;

import java.util.List;
import java.util.Map;

/**
 * @author liuyi
 */
public interface IStockService {
    /**
     * 获取所有库存信息
     * @param pageNum
     * @return
     */
    PageVo<StockVo> findAllByPage(Integer pageNum);

    /**
     * 根据条件获取所有库存信息
     * @param queryVo
     * @return
     */
    PageVo<StockVo> findAllByQueryPage(StockQueryVo queryVo);

    /**
     * 生产产品
     * @param produceConsume
     */
    void produce(ProduceConsume produceConsume);

    /**
     * 查询库存中所有原料的库存
     * @return
     */
    Map<String, Integer> findAllRawByGoodType(Integer typeId);

    /**
     * 仓库预警
     * @return
     */
    List<StockVo> warn();

}
