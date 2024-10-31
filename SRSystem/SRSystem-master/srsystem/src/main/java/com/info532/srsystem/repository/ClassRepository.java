package com.info532.srsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.info532.srsystem.entity.Class; 

@Repository
public interface ClassRepository extends JpaRepository<Class, String> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Class c WHERE c.classId = :classId")
    void deleteByClassId(@Param("classId") String classId);
    
}
