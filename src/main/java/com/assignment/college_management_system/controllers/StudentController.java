package com.assignment.college_management_system.controllers;

import com.assignment.college_management_system.entities.StudentEntity;
import com.assignment.college_management_system.repositories.AdmissionRecordRepository;
import com.assignment.college_management_system.repositories.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students/v1")
public class StudentController {

    AdmissionRecordRepository admissionRecordRepository;
    StudentRepository studentRepository;

    public StudentController(AdmissionRecordRepository admissionRecordRepository, StudentRepository studentRepository) {
        this.admissionRecordRepository = admissionRecordRepository;
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public ResponseEntity<StudentEntity> saveStudent(@RequestBody StudentEntity studentEntity) {
        return new ResponseEntity<>(studentRepository.save(studentEntity), HttpStatus.OK);
    }
}
