package com.info532.srsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.info532.srsystem.entity.Course;
import com.info532.srsystem.entity.CourseId;

@Repository
public interface CourseRepository extends JpaRepository<Course, CourseId> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM courses WHERE dept_code = :deptCode AND course# = :courseNumber", nativeQuery = true)
    void deleteByDepartmentCodeAndCourseNumberNative(@Param("deptCode") String departmentCode, @Param("courseNumber") Integer courseNumber);


}
