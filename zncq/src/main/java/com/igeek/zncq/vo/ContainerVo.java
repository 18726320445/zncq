package com.igeek.zncq.vo;

import com.igeek.zncq.entity.Container;
import lombok.Data;

import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/18 12:53
 * @email liuyia2022@163.com
 */
@Data
public class ContainerVo extends Container {
    private Long num;
    private List<WarehouseVo> warehouseVos;
}
