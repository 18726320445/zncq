package com.igeek.zncq.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/21 11:19
 * @email liuyia2022@163.com
 */
@Data
public class PurchaseDto {
    private Integer pageNum;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date toDate;
    private Integer state;
    private String purchaseNo;
    private Integer goodId;
    private Long num;
}
