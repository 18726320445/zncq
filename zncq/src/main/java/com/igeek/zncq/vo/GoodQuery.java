package com.igeek.zncq.vo;

import lombok.Data;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/13 19:53
 * @email liuyia2022@163.com
 */
@Data
public class GoodQuery {
    private Integer pageNum;
    private String goodName;
    private Integer goodTypeId;
    private String supplierName;
    private Double price;
}
