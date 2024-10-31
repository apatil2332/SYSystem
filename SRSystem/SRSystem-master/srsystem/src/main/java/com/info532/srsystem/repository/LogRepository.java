package com.info532.srsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.info532.srsystem.entity.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {

}
