package com.example.model;

import java.io.Serializable;

public class Account implements Serializable {
//    private int id;
    private String username;
    private String password;

    public Account( String username, String password) {
//        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Account() {
//        id= 0;
        username="";
        password ="";
    }

//    public int getId() {
//        return id;
//    }

//    public void setId(int id) {
//        this.id = id;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
