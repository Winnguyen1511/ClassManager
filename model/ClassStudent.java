package com.example.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ClassStudent implements Serializable {
    private String className;
    private ArrayList<Student> list;

    public ClassStudent(String className, ArrayList<Student> list) {
        this.className = className;
        this.list = list;
    }

    public ClassStudent() {
        className="";
        list = new ArrayList<>();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ArrayList<Student> getList() {
        return list;
    }

    public void setList(ArrayList<Student> list) {
        this.list = list;
    }
}
