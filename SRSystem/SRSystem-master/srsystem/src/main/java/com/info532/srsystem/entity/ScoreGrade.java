package com.info532.srsystem.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "score_grade")
public class ScoreGrade {
    @Id
    @Column(name = "score")
    private Double score;
    
    @Column(name = "lgrade")
    private String letterGrade;
}
