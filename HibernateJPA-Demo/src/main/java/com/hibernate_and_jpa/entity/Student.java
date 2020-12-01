package com.hibernate_and_jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STUDENT_TABLE_TEST")
public class Student {

    @Id
    @GeneratedValue
    public int id;
    public String name;
    public double grade;

    public Student(int id, String name, double grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }
}
