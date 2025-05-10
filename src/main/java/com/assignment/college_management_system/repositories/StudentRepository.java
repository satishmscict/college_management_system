package com.assignment.college_management_system.repositories;

import com.assignment.college_management_system.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

}
