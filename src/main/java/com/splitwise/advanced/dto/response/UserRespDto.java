package com.splitwise.advanced.dto.response;

public class UserRespDto {
    int id;
    String fullName;
    String email;
    String currency;
    String phone;

    public UserRespDto() {

    }

    public UserRespDto(int id, String fullName, String email, String currency, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.currency = currency;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
