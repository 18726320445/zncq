package com.igeek.zncq.vo;

import com.igeek.zncq.entity.Container;
import com.igeek.zncq.entity.Good;
import com.igeek.zncq.entity.Stock;
import com.igeek.zncq.entity.Warehouse;
import lombok.Data;

import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/1 14:27
 * @email liuyia2022@163.com
 */
@Data
public class StockVo extends Stock {
    private Container container;
    private Warehouse warehouse;
    private Long enableNum;
    private List<GoodRawVo> goodRawVos;
    private Good good;
    private GoodRawVo goodRawVo;
}
