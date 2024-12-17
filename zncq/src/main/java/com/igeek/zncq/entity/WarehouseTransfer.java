package com.igeek.zncq.entity;

import java.util.Date;

public class WarehouseTransfer {
    private Integer id;

    private Long num;

    private Integer originalWarehouseId;

    private Integer originalContainerId;

    private Date transferdate;

    private Integer transferContainerId;

    private Integer transferWarehouseId;

    private Integer goodId;

    private String orderNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Integer getOriginalWarehouseId() {
        return originalWarehouseId;
    }

    public void setOriginalWarehouseId(Integer originalWarehouseId) {
        this.originalWarehouseId = originalWarehouseId;
    }

    public Integer getOriginalContainerId() {
        return originalContainerId;
    }

    public void setOriginalContainerId(Integer originalContainerId) {
        this.originalContainerId = originalContainerId;
    }

    public Date getTransferdate() {
        return transferdate;
    }

    public void setTransferdate(Date transferdate) {
        this.transferdate = transferdate;
    }

    public Integer getTransferContainerId() {
        return transferContainerId;
    }

    public void setTransferContainerId(Integer transferContainerId) {
        this.transferContainerId = transferContainerId;
    }

    public Integer getTransferWarehouseId() {
        return transferWarehouseId;
    }

    public void setTransferWarehouseId(Integer transferWarehouseId) {
        this.transferWarehouseId = transferWarehouseId;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }
}