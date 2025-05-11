package com.assignment.college_management_system.services;

import com.assignment.college_management_system.dtos.StudentDTO;
import com.assignment.college_management_system.entities.StudentEntity;
import com.assignment.college_management_system.exceptions.ResourceNotFoundException;
import com.assignment.college_management_system.repositories.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    private ModelMapper modelMapper;
    private final StudentRepository studentRepository;


    public StudentService(ModelMapper modelMapper, StudentRepository studentRepository) {
        this.modelMapper = modelMapper;
        this.studentRepository = studentRepository;
    }

    public StudentDTO saveStudent(StudentDTO studentDTO) {
        StudentEntity studentEntity = modelMapper.map(studentDTO, StudentEntity.class);
        StudentEntity updateStudentEntity = studentRepository.save(studentEntity);
        return modelMapper.map(updateStudentEntity, StudentDTO.class);
    }

    public StudentDTO getStudentById(Long studentId) {
        checkIsEntityExistOrThrow(!studentRepository.existsById(studentId), "Student is not available with the id: " + studentId);

        Optional<StudentEntity> studentEntity = studentRepository.findById(studentId);
        return modelMapper.map(studentEntity, StudentDTO.class);
    }

    private void checkIsEntityExistOrThrow(Boolean isRecordNotExist, String message) {
        if (isRecordNotExist) {
            throw new ResourceNotFoundException(message);
        }
    }
}
