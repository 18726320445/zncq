package com.igeek.zncq.vo;

import lombok.Data;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/2 17:27
 * @email liuyia2022@163.com
 */
@Data
public class OrderGoodVo {
    private String orderNo;
    private Integer goodId;
    private Integer vehicleId;
    private Long num;
}
