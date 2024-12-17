package com.igeek.zncq.vo;

import lombok.Data;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/26 21:59
 * @email liuyia2022@163.com
 */
@Data
public class ContractDto {
    private Integer pageNum;
    private String contractNo;
    private String contractName;
    private String processName;
    private Integer vehicleId;
    private Integer goodId;
    private Long num;
}
