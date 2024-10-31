package com.info532.srsystem.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(CourseId.class)
@Table(name = "courses")
public class Course implements Serializable {

    @Id
    @Column(name = "dept_code")
    private String departmentCode;
    
    @Id
    @Column(name = "course#")
    private Integer courseNumber;
    
    @Column(name = "title")
    private String title;

    
}
