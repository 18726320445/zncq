package com.igeek.zncq.entity;

public class Supplier {
    private Integer id;

    private String name;

    private String phone;

    private String address;

    private String linkman;

    private String email;

    private String remarks;

    public Supplier() {
    }

    public Supplier(Integer id, String name, String phone, String address, String linkman, String email, String remarks) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.linkman = linkman;
        this.email = email;
        this.remarks = remarks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman == null ? null : linkman.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    @Override
    public String toString() {
        return "Supplier{id = " + id + ", name = " + name + ", phone = " + phone + ", address = " + address + ", linkman = " + linkman + ", email = " + email + ", remarks = " + remarks + "}";
    }
}