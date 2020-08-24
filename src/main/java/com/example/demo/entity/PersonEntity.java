package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class PersonEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    long id;

    @Column(name = "name")
    String name;

    @Column(name = "email", unique = true)
    String email;

    @Column(name = "age")
    int age;

    @Column(name = "create_date")
    Date created;

}
