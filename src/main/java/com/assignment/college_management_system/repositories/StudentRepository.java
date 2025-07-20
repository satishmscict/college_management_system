package com.assignment.college_management_system.repositories;

import com.assignment.college_management_system.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    Optional<StudentEntity> findByName(String name);
}
