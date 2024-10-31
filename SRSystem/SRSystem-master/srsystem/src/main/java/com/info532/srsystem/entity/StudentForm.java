package com.info532.srsystem.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudentForm {
    private String bNumber;
    private String firstName;
    private String lastName;
    private String studentLevel;
    private String gpa;
    private String email;
    private String birthDate;
}

