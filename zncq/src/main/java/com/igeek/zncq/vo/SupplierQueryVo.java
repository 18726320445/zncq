package com.igeek.zncq.vo;

import com.igeek.zncq.entity.Supplier;
import lombok.Data;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/11 10:04
 * @email liuyia2022@163.com
 */
@Data
public class SupplierQueryVo {
    Integer pageNum;
    private String name;
    private String address;

    @Override
    public String toString() {
        return "SupplierQueryVo{} " + super.toString();
    }
}
