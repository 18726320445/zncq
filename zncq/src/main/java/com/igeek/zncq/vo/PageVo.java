package com.igeek.zncq.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/8 12:34
 * @email liuyia2022@163.com
 */
@Data
public class PageVo<T> {
    /**
     * currentPage
     */
    private Integer currentPage;
    private Integer pageSize;
    private Integer total;
    private List<T> data;
}
