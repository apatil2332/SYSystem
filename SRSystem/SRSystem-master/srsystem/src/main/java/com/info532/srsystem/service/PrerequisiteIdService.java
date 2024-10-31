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

import com.info532.srsystem.entity.PrerequisiteId;

import oracle.jdbc.OracleTypes;

@Service
public class PrerequisiteIdService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<PrerequisiteId> showPrerequites() {
        List<PrerequisiteId> prerequisites = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            String plsqlBlock = "{ ? = call srsystem.get_prerequisites_cursor }";
            try (CallableStatement stmt = connection.prepareCall(plsqlBlock)) {
                stmt.registerOutParameter(1, OracleTypes.CURSOR);
                stmt.execute();
                try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                    while (rs.next()) {
                        PrerequisiteId prereqs = new PrerequisiteId();
                        prereqs.setDepartmentCode(rs.getString(1));
                        prereqs.setCourseNumber(rs.getInt(2));
                        prereqs.setPreDepartmentCode(rs.getString(3)); 
                        prereqs.setPreCourseNumber(rs.getInt(4)); 
                        prerequisites.add(prereqs);
                    }
                }
            }
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
        }
        return prerequisites;
    }

}
