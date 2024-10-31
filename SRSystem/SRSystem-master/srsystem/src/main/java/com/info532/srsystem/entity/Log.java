package com.info532.srsystem.entity;

import javax.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "logs")
public class Log {
    @Id
    @Column(name = "log#")
    private Integer logNumber;
    
    @Column(name = "user_name")
    private String userName;
    
    @Column(name = "op_time")
    private Date operationTime;
    
    @Column(name = "table_name")
    private String tableName;
    
    @Column(name = "operation")
    private String operation;
    
    @Column(name = "tuple_keyvalue")
    private String tupleKeyValue;
}
