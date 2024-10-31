package com.info532.srsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "students")
public class Student {
    @Id
    @Column(name = "B#")
    private String bNumber;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "st_level")
    private String studentLevel;
    
    @Column(name = "gpa")
    private Double gpa;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "bdate")
    private Date birthDate;
}
