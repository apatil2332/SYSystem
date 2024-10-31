package com.info532.srsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.info532.srsystem.entity.CourseCredit;

@Repository
public interface CourseCreditRepository extends JpaRepository<CourseCredit, Integer> {

    @Modifying
    @Query(value = "DELETE FROM course_credit WHERE course# = :courseNumber", nativeQuery = true)
    void deleteByCourseNumberNativeQuery(@Param("courseNumber") Integer courseNumber);

}
