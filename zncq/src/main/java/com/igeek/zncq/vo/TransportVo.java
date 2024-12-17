package com.igeek.zncq.vo;

import com.igeek.zncq.entity.Order;
import com.igeek.zncq.entity.Transport;
import com.igeek.zncq.entity.Vehicle;
import lombok.Data;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/1 09:20
 * @email liuyia2022@163.com
 */
@Data
public class TransportVo extends Transport {
    private Vehicle vehicle;
    private Order order;
}
