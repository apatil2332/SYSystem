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
import com.info532.srsystem.entity.Prerequisite;
import com.info532.srsystem.entity.PrerequisiteId;
import com.info532.srsystem.entity.Student;

import oracle.jdbc.OracleTypes;

@Service
public class PrerequisiteService {

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


    public List<PrerequisiteId> getPrerequisitesByDeptCodeAndCourseNumber(String deptCode, Integer courseNumber) {
        List<PrerequisiteId> prerequisitesIds = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            String plsqlBlock = "begin srsystem.get_prerequisites_by_course(:1, :2, :3, :4); end;";
            try (CallableStatement stmt = connection.prepareCall(plsqlBlock)) {
                stmt.setString(1, deptCode);
                stmt.setInt(2, courseNumber);
                stmt.registerOutParameter(3, OracleTypes.CURSOR);
                stmt.registerOutParameter(4, OracleTypes.VARCHAR);
                stmt.execute();

                String result = (String) stmt.getObject(4);
                
                if(null != result){
                    PrerequisiteId prerequisiteId = new PrerequisiteId();
                    prerequisiteId.setDepartmentCode(result);
                    prerequisitesIds.add(prerequisiteId);
                }else{
                    try (ResultSet rs = (ResultSet)stmt.getObject(3)) {
                        while (rs.next()) {
                            PrerequisiteId prerequisiteId = new PrerequisiteId();
                            prerequisiteId.setDepartmentCode(rs.getString("prerequisite"));
                            prerequisitesIds.add(prerequisiteId);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
        }
        return prerequisitesIds;
    }

}
