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

import com.info532.srsystem.entity.CourseCredit;

import oracle.jdbc.OracleTypes;

@Service
public class CourseCreditService {
     @Autowired
    private JdbcTemplate jdbcTemplate;

        public List<CourseCredit> showCourseCredits() {
        List<CourseCredit> coursecredits = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            String plsqlBlock = "{ ? = call srsystem.get_course_credit_cursor }";
            try (CallableStatement stmt = connection.prepareCall(plsqlBlock)) {
                stmt.registerOutParameter(1, OracleTypes.CURSOR);
                stmt.execute();
                try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                    while (rs.next()) {
                        CourseCredit cc = new CourseCredit();
                        cc.setCourseNumber(rs.getInt(1));
                        cc.setCredits(rs.getInt(2));
                        coursecredits.add(cc);
                    }
                }
            }
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
        }
        return coursecredits;
    }

}

