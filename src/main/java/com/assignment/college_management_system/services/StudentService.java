package com.assignment.college_management_system.services;

import com.assignment.college_management_system.dtos.StudentDTO;

import java.util.List;

public interface StudentService {

    StudentDTO assignStudentToSubject(Long studentId, Long subjectId);

    StudentDTO findStudentByName(String name);

    List<StudentDTO> getAllStudents();

    StudentDTO getStudentById(Long studentId);

    StudentDTO saveStudent(StudentDTO studentDTO);
}
