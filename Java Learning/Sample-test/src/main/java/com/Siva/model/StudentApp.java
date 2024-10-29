package com.Siva.model;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class StudentApp {
    // Getters and setters
    private String name;
    private int age;
    private String grade;
    private LocalDate dob;

    public StudentApp(String name, int age, String grade, LocalDate dob) {
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.dob = dob;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public void setDob(LocalDate dob) {this.dob = dob;}
}
