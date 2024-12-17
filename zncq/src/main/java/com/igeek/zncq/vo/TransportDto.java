package com.igeek.zncq.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/2 15:58
 * @email liuyia2022@163.com
 */
@Data
public class TransportDto {
    private String loginNumber;
    private String orderNo;
    private Integer goodId;
    private Long num;
    private Integer vehicleId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date enddate;
}
