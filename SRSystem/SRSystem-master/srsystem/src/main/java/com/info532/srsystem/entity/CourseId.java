package com.info532.srsystem.entity;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CourseId implements Serializable {

    private String departmentCode;
    private Integer courseNumber;

}
