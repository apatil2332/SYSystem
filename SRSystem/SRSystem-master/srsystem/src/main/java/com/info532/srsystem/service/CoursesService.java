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

import com.info532.srsystem.entity.Course;
import com.info532.srsystem.entity.CourseId;
import com.info532.srsystem.repository.CourseCreditRepository;
import com.info532.srsystem.repository.CourseRepository;
import com.info532.srsystem.repository.PrerequisiteRepository;
import com.info532.srsystem.repository.StudentRepository;

import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;
import oracle.jdbc.OracleTypes;

@Service
public class CoursesService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseCreditRepository courseCreditRepository;

    @Autowired
    private PrerequisiteRepository prerequisiteRepository;

        public List<Course> showCourses() {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            String plsqlBlock = "{ ? = call srsystem.get_courses_cursor }";
            try (CallableStatement stmt = connection.prepareCall(plsqlBlock)) {
                stmt.registerOutParameter(1, OracleTypes.CURSOR);
                stmt.execute();
                try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                    while (rs.next()) {
                        Course course = new Course();
                        course.setDepartmentCode(rs.getString(1));
                        course.setCourseNumber(rs.getInt(2));
                        course.setTitle(rs.getString(3));
                        courses.add(course);
                    }
                }
            }
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
        }
        return courses;
    }

    public boolean deleteCourseByDeptCodeAndCourseNo(String departmentCode, Integer courseNumber) {
        CourseId courseId = new CourseId();
        courseId.setDepartmentCode(departmentCode);
        courseId.setCourseNumber(courseNumber);
        try{
            courseRepository.deleteById(courseId);
            if(courseCreditRepository.existsById(courseNumber)){
                courseCreditRepository.deleteById(courseNumber);
            }
            return true;
        }catch(Exception e){
            return false;
        }
       
    }

    @Modifying
    @Transactional
    public void save(Course course) {
        courseRepository.save(course);
    }

}
