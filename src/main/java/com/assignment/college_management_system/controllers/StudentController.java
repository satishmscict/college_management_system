package com.assignment.college_management_system.controllers;

import com.assignment.college_management_system.dtos.StudentDTO;
import com.assignment.college_management_system.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping(path = "/studentId/{studentId}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getStudentById(studentId));
    }

    @GetMapping(path = "/studentName/{studentName}")
    public ResponseEntity<StudentDTO> getStudentByName(@PathVariable String studentName) {
        return ResponseEntity.ok(studentService.findStudentByName(studentName));
    }

    @PostMapping
    public ResponseEntity<StudentDTO> saveStudent(@RequestBody @Valid StudentDTO studentDTO) {
        return new ResponseEntity<>(studentService.saveStudent(studentDTO), HttpStatus.CREATED);
    }

    @PutMapping(path = "/studentId/{studentId}/subjectId/{subjectId}")
    public ResponseEntity<StudentDTO> assignStudentToSubject(
            @PathVariable Long studentId,
            @PathVariable Long subjectId
    ) {
        return ResponseEntity.ok(studentService.assignStudentToSubject(studentId, subjectId));
    }
}
