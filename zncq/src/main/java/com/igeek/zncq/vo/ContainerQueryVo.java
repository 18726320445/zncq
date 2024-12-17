package com.igeek.zncq.vo;

import lombok.Data;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/16 17:22
 * @email liuyia2022@163.com
 */
@Data
public class ContainerQueryVo {
    private Integer pageNum;
    private String name;
    private String type;
    private Long maxCapacity;
}
