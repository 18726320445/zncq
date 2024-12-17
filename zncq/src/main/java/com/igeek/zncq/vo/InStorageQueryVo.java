package com.igeek.zncq.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/23 19:35
 * @email liuyia2022@163.com
 */
@Data
public class InStorageQueryVo {
    private Integer pageNum;
    private String orderNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date toDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;
    private String goodName;
    private String warehouseName;
}
