package com.example.demo.repository;

import com.example.demo.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    @Query("SELECT pe FROM PersonEntity pe WHERE pe.id = :id")
    PersonEntity findPersonById(Long id);

    PersonEntity findByEmailContainingIgnoreCase(String email);

    PersonEntity findFirstByOrderByIdDesc();

}
