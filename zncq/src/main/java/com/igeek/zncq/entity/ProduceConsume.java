package com.igeek.zncq.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ProduceConsume {
    private Integer id;

    private Integer warehouseId;

    private Long rawNum;

    private Integer rawId;

    private Integer rawContainerId;

    private Integer goodId;

    private Long goodNum;

    private Integer goodContainerId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getRawNum() {
        return rawNum;
    }

    public void setRawNum(Long rawNum) {
        this.rawNum = rawNum;
    }

    public Integer getRawId() {
        return rawId;
    }

    public void setRawId(Integer rawId) {
        this.rawId = rawId;
    }

    public Integer getRawContainerId() {
        return rawContainerId;
    }

    public void setRawContainerId(Integer rawContainerId) {
        this.rawContainerId = rawContainerId;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Long getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Long goodNum) {
        this.goodNum = goodNum;
    }

    public Integer getGoodContainerId() {
        return goodContainerId;
    }

    public void setGoodContainerId(Integer goodContainerId) {
        this.goodContainerId = goodContainerId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}