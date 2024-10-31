package com.info532.srsystem.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "prerequisites")
public class Prerequisite {

    @EmbeddedId
    private PrerequisiteId id;

}
