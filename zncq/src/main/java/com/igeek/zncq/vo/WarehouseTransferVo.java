package com.igeek.zncq.vo;

import com.igeek.zncq.entity.*;
import lombok.Data;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/2 09:48
 * @email liuyia2022@163.com
 */
@Data
public class WarehouseTransferVo extends WarehouseTransfer {
    private Warehouse ow;
    private Warehouse tw;
    private Container oc;
    private Container tc;
    private Good good;
    private Order order;
}
