package com.example.model;

import java.io.Serializable;

public class Student implements Serializable {
    private String name;
    private String phone;

    public Student() {
        name ="";
        phone="";
    }

    public Student(String name, String phone) {
        this.name = name;
        this.phone = phone;
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
}
