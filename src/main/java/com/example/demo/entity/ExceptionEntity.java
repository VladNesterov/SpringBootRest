package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class ExceptionEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    long id;

    @Column(name = "msg")
    String msg;

    @Column(name = "create_date")
    Date created;

}
