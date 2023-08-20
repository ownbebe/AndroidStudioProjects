package com.example.sqlitetest;

import java.io.Serializable;

public class Student implements Serializable {
    private int id;
    private String name;
    private String lastName;
    private int grade;

    public Student(int id, String name, String lastName, int grade) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}

