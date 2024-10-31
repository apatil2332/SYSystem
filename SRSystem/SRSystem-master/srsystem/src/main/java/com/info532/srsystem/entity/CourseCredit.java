package com.info532.srsystem.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "course_credit")
public class CourseCredit {
    @Id
    @Column(name = "course#")
    private Integer courseNumber;
    
    @Column(name = "credits")
    private Integer credits;
}
