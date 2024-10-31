package com.info532.srsystem.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "classes")
public class Class {
    @Id
    @Column(name = "classid")
    private String classId;
    
    @Column(name = "dept_code")
    private String departmentCode;
    
    @Column(name = "course#")
    private Integer courseNumber;
    
    @Column(name = "sect#")
    private Integer sectionNumber;
    
    @Column(name = "year")
    private Integer year;
    
    @Column(name = "semester")
    private String semester;
    
    @Column(name = "limit")
    private Integer limit;
    
    @Column(name = "class_size")
    private Integer classSize;
    
    @Column(name = "room")
    private String room;
}
