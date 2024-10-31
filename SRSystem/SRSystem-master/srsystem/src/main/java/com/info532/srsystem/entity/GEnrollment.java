package com.info532.srsystem.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "g_enrollments")
public class GEnrollment {

    @EmbeddedId
    private GEnrollmentId id;

    @ManyToOne
    @JoinColumn(name = "score")
    private ScoreGrade score;

}
