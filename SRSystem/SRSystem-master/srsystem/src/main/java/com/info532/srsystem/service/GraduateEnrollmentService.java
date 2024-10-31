package com.info532.srsystem.service;

import java.sql.CallableStatement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.info532.srsystem.entity.Class;
import com.info532.srsystem.entity.Course;
import com.info532.srsystem.entity.GEnrollment;
import com.info532.srsystem.entity.GEnrollmentId;
import com.info532.srsystem.entity.ScoreGrade;
import com.info532.srsystem.entity.Student;

import oracle.jdbc.OracleTypes;

import org.springframework.stereotype.Service;

@Service
public class GraduateEnrollmentService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

        public List<GEnrollment> showGEnrollmentIds() {
        List<GEnrollment> enrollments = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            String plsqlBlock = "{ ? = call srsystem.get_courses_cursor }";
            try (CallableStatement stmt = connection.prepareCall(plsqlBlock)) {
                stmt.registerOutParameter(1, OracleTypes.CURSOR);
                stmt.execute();
                try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                    while (rs.next()) {
                        Student student = new Student();
                        GEnrollmentId enrollmentId = new GEnrollmentId();
                        ScoreGrade score = new ScoreGrade();
                        GEnrollment enrollment = new GEnrollment();
                        com.info532.srsystem.entity.Class clas = new Class();
                        student.setBNumber(rs.getString(1));
                        clas.setClassId(rs.getString(2));
                        score.setScore(rs.getDouble(3));

                        enrollmentId.setClassId(clas);
                        enrollmentId.setStudent(student);
                        enrollment.setId(enrollmentId);
                        enrollment.setScore(score);
                        enrollments.add(enrollment);
                    }
                }
            }
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
        }
        return enrollments;
    }
}
