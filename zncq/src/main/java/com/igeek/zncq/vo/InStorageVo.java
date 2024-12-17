package com.igeek.zncq.vo;

import com.igeek.zncq.entity.Container;
import com.igeek.zncq.entity.Good;
import com.igeek.zncq.entity.InStorage;
import com.igeek.zncq.entity.Warehouse;
import lombok.Data;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/23 13:17
 * @email liuyia2022@163.com
 */
@Data
public class InStorageVo extends InStorage {
    private Good good;
    private Container container;
    private Warehouse warehouse;
}
