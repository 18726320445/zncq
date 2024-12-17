package com.igeek.zncq.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/28 11:30
 * @email liuyia2022@163.com
 */
@Data
public class OutStorageQueryVo {
    private Integer pageNum;
    private String orderNo;
    private String goodName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date toDate;
}
