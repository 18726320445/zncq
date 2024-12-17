package com.igeek.zncq.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/28 18:27
 * @email liuyia2022@163.com
 */
@Data
public class OutStorageDto {
    private String orderNo;
    private Integer goodId;
    private Long num;
    private Integer warehouseId;
    private Integer containerId;
    private String vehicleNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date outdate;
}
