package com.info532.srsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info532.srsystem.entity.Student;
import com.info532.srsystem.repository.StudentRepository;

import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Student> showStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            String plsqlBlock = "{ ? = call srsystem.get_students_cursor }";
            try (CallableStatement stmt = connection.prepareCall(plsqlBlock)) {
                stmt.registerOutParameter(1, OracleTypes.CURSOR);
                stmt.execute();
                try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                    while (rs.next()) {
                        Student student = new Student();
                        student.setBNumber(rs.getString("B#"));
                        student.setFirstName(rs.getString("first_name"));
                        student.setLastName(rs.getString("last_name"));
                        student.setStudentLevel(rs.getString("st_level"));
                        student.setGpa(rs.getDouble("gpa"));
                        student.setEmail(rs.getString("email"));
                        student.setBirthDate(rs.getDate("bdate"));
                        students.add(student);
                    }
                }
            }
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
        }
        return students;
    }
    
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            String plsqlBlock = "{ ? = call enrollment_pkg.show_students }";
            try (CallableStatement stmt = connection.prepareCall(plsqlBlock)) {
                stmt.execute();
                try (ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                        Student student = new Student();
                        student.setBNumber(rs.getString("B#"));
                        student.setFirstName(rs.getString("first_name"));
                        student.setLastName(rs.getString("last_name"));
                        // Set other properties
                        students.add(student);
                    }
                }
            }
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
        }
        return students;
    }

    public String dropStudent(String bNumber) {
        String result = "ERROR";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            String plsqlBlock = "begin srsystem.delete_student(:1, :2); end;";
            try (CallableStatement stmt = connection.prepareCall(plsqlBlock)) {
                stmt.setString(1, bNumber);
                stmt.registerOutParameter(2, OracleTypes.VARCHAR);
                stmt.execute();
                result = (String) stmt.getObject(2);
            }
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
        }
        return result;
    }
    

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentByBNumber(String bNumber) {
        return studentRepository.findById(bNumber).orElse(null);
    }

    @Modifying
    @Transactional
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(String bNumber) {
        studentRepository.deleteById(bNumber);
    }

}
