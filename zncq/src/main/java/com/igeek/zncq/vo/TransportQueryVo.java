package com.igeek.zncq.vo;

import lombok.Data;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/1 13:50
 * @email liuyia2022@163.com
 */
@Data
public class TransportQueryVo {
    private Integer pageNum;
    private String orderNo;
    private String vehicleNo;
    private Integer state = 1;
}
