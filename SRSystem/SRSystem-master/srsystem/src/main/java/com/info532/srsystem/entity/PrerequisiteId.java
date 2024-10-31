package com.info532.srsystem.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class PrerequisiteId implements Serializable {

    @Column(name = "dept_code")
    private String departmentCode;

    @Column(name = "course#")
    private Integer courseNumber;

    @Column(name = "pre_dept_code")
    private String preDepartmentCode;

    @Column(name = "pre_course#")
    private Integer preCourseNumber;

}
