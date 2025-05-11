package com.assignment.college_management_system.controllers;

import com.assignment.college_management_system.entities.StudentEntity;
import com.assignment.college_management_system.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students/v1")
public class StudentController {

    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentEntity> saveStudent(@RequestBody StudentEntity studentEntity) {
        return new ResponseEntity<>(studentService.saveStudent(studentEntity), HttpStatus.OK);
    }
}
