package com.example.demo.repository;

import com.example.demo.entity.ExceptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExceptionRepository extends JpaRepository<ExceptionEntity, Long> {

    ExceptionEntity findFirstByOrderByIdDesc();

}
