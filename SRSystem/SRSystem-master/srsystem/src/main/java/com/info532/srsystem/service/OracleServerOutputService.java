package com.info532.srsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class OracleServerOutputService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void initializeServerOutput() {
        jdbcTemplate.execute("BEGIN EXECUTE IMMEDIATE 'ALTER SESSION SET SERVEROUTPUT ON'; END;");
    }
}
