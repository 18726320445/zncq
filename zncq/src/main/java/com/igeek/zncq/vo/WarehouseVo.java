package com.igeek.zncq.vo;

import com.igeek.zncq.entity.Warehouse;
import lombok.Data;

import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/18 13:01
 * @email liuyia2022@163.com
 */
@Data
public class WarehouseVo extends Warehouse {
    private List<ContainerVo> containerVos;
}
