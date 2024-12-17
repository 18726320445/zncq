package com.igeek.zncq.vo;

import lombok.Data;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/25 11:21
 * @email liuyia2022@163.com
 */
@Data
public class CustomerQueryVo {
    private Integer pageNum;
    private String customerName;
    private String address;
}
