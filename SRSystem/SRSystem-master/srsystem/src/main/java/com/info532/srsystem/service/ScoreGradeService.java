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
import com.info532.srsystem.entity.ScoreGrade;

import oracle.jdbc.OracleTypes;


@Service
public class ScoreGradeService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

        public List<ScoreGrade> showScoreGrades() {
        List<ScoreGrade> sgrades = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            String plsqlBlock = "{ ? = call srsystem.get_score_grade_cursor }";
            try (CallableStatement stmt = connection.prepareCall(plsqlBlock)) {
                stmt.registerOutParameter(1, OracleTypes.CURSOR);
                stmt.execute();
                try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                    while (rs.next()) {
                        ScoreGrade sg = new ScoreGrade();
                        sg.setScore(rs.getDouble("score"));
                        sg.setLetterGrade(rs.getString("lgrade"));
                        sgrades.add(sg);
                    }
                }
            }
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
        }
        return sgrades;
    }

}
