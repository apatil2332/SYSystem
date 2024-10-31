package com.info532.srsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.info532.srsystem.entity.GEnrollment;
import com.info532.srsystem.entity.GEnrollmentId;

@Repository
public interface GEnrollmentRepository extends JpaRepository<GEnrollment, GEnrollmentId> {


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM g_enrollments WHERE classid = :classId", nativeQuery = true)
    void deleteByClassId(String classId);

}
