package com.example.SpringWithMongo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document

public class Student {
    @Transient
    public static final String SEQUENCE_NAME = "setudents_sequence";
    //private String id;
    @Id
    private int rno;

    private String firstName;
    private String address;
    private int age;

    public Student() {
    }

    public Student(int rno, String firstName, String address, int age) {

        this.rno = rno;
        this.firstName = firstName;
        this.address = address;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +

                ", rno=" + rno +
                ", firstName='" + firstName + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }


    public int getRno() {
        return rno;
    }

    public void setRno(int rno) {
        this.rno = rno;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

