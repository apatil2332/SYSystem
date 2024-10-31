package com.info532.srsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.info532.srsystem.entity.Prerequisite;
import com.info532.srsystem.entity.PrerequisiteId;

@Repository
public interface PrerequisiteRepository extends JpaRepository<Prerequisite, PrerequisiteId> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Prerequisite p WHERE (p.id.courseNumber = :courseNumber AND p.id.departmentCode = :departmentCode) OR (p.id.preCourseNumber = :courseNumber AND p.id.preDepartmentCode = :departmentCode)")
    void deleteByCourseNumberAndDepartmentCode(Integer courseNumber, String departmentCode);
 
}
