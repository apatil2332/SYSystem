package com.info532.srsystem.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class GEnrollmentId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "g_B#")
    private Student student;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "classid")
    private Class classId;

}
