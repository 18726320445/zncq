package com.igeek.zncq.entity;

import java.util.Date;

public class Transport {
    private Integer id;

    private Integer vehicleId;

    private String startAddress;

    private String midAddress;

    private String endAddress;

    private Date startdate;

    private Date expectedEnddate;

    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress == null ? null : startAddress.trim();
    }

    public String getMidAddress() {
        return midAddress;
    }

    public void setMidAddress(String midAddress) {
        this.midAddress = midAddress == null ? null : midAddress.trim();
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress == null ? null : endAddress.trim();
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getExpectedEnddate() {
        return expectedEnddate;
    }

    public void setExpectedEnddate(Date expectedEnddate) {
        this.expectedEnddate = expectedEnddate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}