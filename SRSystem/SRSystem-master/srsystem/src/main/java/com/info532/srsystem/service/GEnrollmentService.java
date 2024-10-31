package com.info532.srsystem.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.integrator.spi.Integrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.info532.srsystem.entity.Class;
import com.info532.srsystem.entity.GEnrollment;
import com.info532.srsystem.entity.GEnrollmentId;
import com.info532.srsystem.entity.ScoreGrade;
import com.info532.srsystem.entity.Student;

import oracle.jdbc.OracleTypes;

@Service
public class GEnrollmentService {

    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<GEnrollment> showGEnrollments() {
        List<GEnrollment> enrollments = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            String plsqlBlock = "{ ? = call srsystem.get_g_enrollments_cursor }";
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

    public String enroll(String bNumber, String classId) {
        String result = "ERROR";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            String plsqlBlock = "begin srsystem.enroll_graduate_student(:1, :2, :3); end;";
            try (CallableStatement stmt = connection.prepareCall(plsqlBlock)) {
                stmt.setString(1, bNumber);
                stmt.setString(2, classId);
                stmt.registerOutParameter(3, OracleTypes.VARCHAR);
                stmt.execute();
                result = (String) stmt.getObject(3);
            }
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
        }
        return result;
    }

    public String dropEnroll(String bNumber, String classId) {
        String result = "ERROR";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            String plsqlBlock = "begin srsystem.drop_graduate_student(:1, :2, :3); end;";
            try (CallableStatement stmt = connection.prepareCall(plsqlBlock)) {
                stmt.setString(1, bNumber);
                stmt.setString(2, classId);
                stmt.registerOutParameter(3, OracleTypes.VARCHAR);
                stmt.execute();
                result = (String) stmt.getObject(3);
            }
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
        }
        return result;
    }

}
