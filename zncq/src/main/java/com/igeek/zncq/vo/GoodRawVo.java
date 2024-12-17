package com.igeek.zncq.vo;

import com.igeek.zncq.entity.Good;
import com.igeek.zncq.entity.Supplier;
import lombok.Data;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/8 16:05
 * @email liuyia2022@163.com
 */
@Data
public class GoodRawVo extends Good {
    private Long num;
    private Supplier supplier;
    private Long enableNum;

    @Override
    public String toString() {
        return "GoodVo{" +
                "supplier=" + supplier +
                "} " + super.toString();
    }
}
