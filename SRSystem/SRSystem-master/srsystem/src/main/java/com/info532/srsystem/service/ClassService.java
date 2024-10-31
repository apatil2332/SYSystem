package com.info532.srsystem.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info532.srsystem.entity.Class;
import com.info532.srsystem.entity.Student;
import com.info532.srsystem.repository.ClassRepository;
import com.info532.srsystem.repository.GEnrollmentRepository;
import com.info532.srsystem.repository.StudentRepository;

import oracle.jdbc.OracleTypes;

@Service
public class ClassService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private GEnrollmentRepository gEnrollmentRepository;

    public List<Student> getStudentsInClass(String classId) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            String plsqlBlock = "begin srsystem.get_students_by_class_id(:1, :2); end;";
            try (CallableStatement stmt = connection.prepareCall(plsqlBlock)) {
                stmt.setString(1, classId);
                stmt.registerOutParameter(2, OracleTypes.CURSOR);
                stmt.execute();
                try (ResultSet rs = (ResultSet)stmt.getObject(2)) {
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

    public List<Class> showClasses() {
        List<Class> classes = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            String plsqlBlock = "{ ? = call srsystem.get_classes_cursor }";
            try (CallableStatement stmt = connection.prepareCall(plsqlBlock)) {
                stmt.registerOutParameter(1, OracleTypes.CURSOR);
                stmt.execute();
                try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                    while (rs.next()) {
                        Class clas = new Class();
                        clas.setClassId(rs.getString(1));
                        clas.setDepartmentCode(rs.getString(2));
                        clas.setCourseNumber(rs.getInt(3));
                        clas.setSectionNumber(rs.getInt(4)); 
                        clas.setYear(rs.getInt(5)); 
                        clas.setSemester(rs.getString(6)); 
                        clas.setLimit(rs.getInt(7)); 
                        clas.setClassSize(rs.getInt(8)); 
                        clas.setRoom(rs.getString(9)); 
                        classes.add(clas);
                    }
                }
            }
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
        }
        return classes;
    }

    public boolean deleteClassById(String classId) {
        try {
            classRepository.deleteByClassId(classId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Modifying
    @Transactional
    public void save(Class clas) {
        classRepository.save(clas);
    }
    
}
