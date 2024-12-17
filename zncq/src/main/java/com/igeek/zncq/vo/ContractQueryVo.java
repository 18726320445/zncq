package com.igeek.zncq.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/27 20:58
 * @email liuyia2022@163.com
 */
@Data
public class ContractQueryVo {
    private Integer pageNum;
    private String contractNo;
    private String customerName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date toDate;
}
