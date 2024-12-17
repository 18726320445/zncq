package com.igeek.zncq.vo;

import com.igeek.zncq.entity.Container;
import com.igeek.zncq.entity.OutStorage;
import com.igeek.zncq.entity.Vehicle;
import com.igeek.zncq.entity.Warehouse;
import lombok.Data;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/28 09:25
 * @email liuyia2022@163.com
 */
@Data
public class OutStorageVo extends OutStorage {
    private Warehouse warehouse;
    private Container container;
    private GoodRawVo good;
    private Vehicle vehicle;
}
