package com.igeek.zncq.vo;


import com.igeek.zncq.entity.Customer;
import com.igeek.zncq.entity.Order;
import com.igeek.zncq.entity.Vehicle;
import lombok.Data;

import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/21 13:58
 * @email liuyia2022@163.com
 */
@Data
public class OrderVo extends Order {
    private Customer customer;
    private Vehicle vehicle;
    private Long totalNum;
    private List<GoodRawVo> goods;
}
