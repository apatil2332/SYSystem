package com.info532.srsystem.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.info532.srsystem.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
}
