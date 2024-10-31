package com.info532.srsystem.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.info532.srsystem.entity.Course;
import com.info532.srsystem.entity.Log;

import oracle.jdbc.OracleTypes;

import org.springframework.stereotype.Service;

@Service
public class LogService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Log> showLogs() {
        List<Log> logs = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            String plsqlBlock = "{ ? = call srsystem.get_logs_cursor }";
            try (CallableStatement stmt = connection.prepareCall(plsqlBlock)) {
                stmt.registerOutParameter(1, OracleTypes.CURSOR);
                stmt.execute();
                try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                    while (rs.next()) {
                        Log log = new Log();
                        log.setLogNumber(rs.getInt("log#"));
                        log.setUserName(rs.getString("user_name"));
                        log.setOperationTime(rs.getDate("op_time"));
                        log.setTableName(rs.getString("table_name"));
                        log.setOperation(rs.getString("operation"));
                        log.setTupleKeyValue(rs.getString("tuple_keyvalue"));
                        logs.add(log);
                    }
                }
            }
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
        }
        return logs;
    }

}
