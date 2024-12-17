package com.igeek.zncq.vo;

import lombok.Data;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/1 18:14
 * @email liuyia2022@163.com
 */
@Data
public class StockQueryVo {
    private Integer pageNum;
    private String warehouseName;
    private String goodName;
}
