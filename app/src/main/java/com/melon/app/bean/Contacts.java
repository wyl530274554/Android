package com.melon.app.bean;

public class Contacts {
    private Long id;
    private String name;
    private String phone;
    private Boolean isDeleted;

    public Contacts(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Contacts(Long id, String name, String phone, Boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
