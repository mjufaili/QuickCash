package com.example.g15_gp.user;

public class User {

    public String fullName;
    public String email;
    public String phone;
    public String location;
    public String userType;
    public User(){
    }

    public User(String fullName, String email, String phone, String location, String userType){
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.userType = userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }


    public String getFullName() {
        return fullName;
    }


    public String getPhone() {
        return phone;
    }


    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}