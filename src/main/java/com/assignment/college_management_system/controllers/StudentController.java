package com.assignment.college_management_system.controllers;

import com.assignment.college_management_system.dtos.StudentDTO;
import com.assignment.college_management_system.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students/v1")
public class StudentController {

    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
    return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping(path = "/{studentId}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getStudentById(studentId));
    }

    @PostMapping
    public ResponseEntity<StudentDTO> saveStudent(@RequestBody @Valid StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.saveStudent(studentDTO));
    }

    @PutMapping(path = "/{studentId}/subject/{subjectId}")
    public ResponseEntity<StudentDTO> assignStudentToSubject(
            @PathVariable Long studentId,
            @PathVariable Long subjectId
    ) {
        return ResponseEntity.ok(studentService.assignStudentToSubject(studentId, subjectId));
    }
}
