package com.igeek.zncq.vo;

import com.igeek.zncq.entity.Contract;
import com.igeek.zncq.entity.Customer;
import lombok.Data;

import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/25 14:45
 * @email liuyia2022@163.com
 */
@Data
public class ContractVo extends Contract {
    private Double total;
    private String processName;
    private Long num;
    private List<GoodRawVo> goods;
    private Customer customer;
}
