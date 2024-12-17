package com.igeek.zncq.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/1 20:13
 * @email liuyia2022@163.com
 */
@Data
public class WarehouseTransferDto {
    private String processName;
    private Integer goodId;
    private Long num;
    private Integer originalWarehouseId;
    private Integer originalContainerId;
    private Integer transferWarehouseId;
    private Integer transferContainerId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date transferdate;
    private Integer vehicleId;
}
