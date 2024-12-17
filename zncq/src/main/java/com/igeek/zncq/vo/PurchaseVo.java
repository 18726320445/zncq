package com.igeek.zncq.vo;


import com.igeek.zncq.entity.Purchase;
import lombok.Data;

import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/20 21:29
 * @email liuyia2022@163.com
 */
@Data
public class PurchaseVo extends Purchase {
    private Double total;
    private List<GoodRawVo> goods;
}
