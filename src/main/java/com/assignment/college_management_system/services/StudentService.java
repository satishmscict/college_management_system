package com.assignment.college_management_system.services;

import com.assignment.college_management_system.dtos.StudentDTO;
import com.assignment.college_management_system.entities.StudentEntity;
import com.assignment.college_management_system.entities.SubjectEntity;
import com.assignment.college_management_system.repositories.StudentRepository;
import com.assignment.college_management_system.repositories.SubjectRepository;
import com.assignment.college_management_system.utils.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final ValidationUtils validationUtils;

    public StudentService(
            ModelMapper modelMapper,
            StudentRepository studentRepository,
            SubjectRepository subjectRepository,
            ValidationUtils validationUtils
    ) {
        this.modelMapper = modelMapper;
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.validationUtils = validationUtils;
    }

    public StudentDTO saveStudent(StudentDTO studentDTO) {
        StudentEntity studentEntity = modelMapper.map(studentDTO, StudentEntity.class);
        StudentEntity updateStudentEntity = studentRepository.save(studentEntity);
        return modelMapper.map(updateStudentEntity, StudentDTO.class);
    }

    public StudentDTO getStudentById(Long studentId) {
        validationUtils.checkIsResourceExistOrThrow(studentRepository.existsById(studentId), "Student is not available with the id: " + studentId);

        Optional<StudentEntity> studentEntity = studentRepository.findById(studentId);
        return modelMapper.map(studentEntity, StudentDTO.class);
    }

    public StudentDTO assignStudentToSubject(Long studentId, Long subjectId) {
        Optional<StudentEntity> studentEntity = studentRepository.findById(studentId);
        validationUtils.checkIsResourceExistOrThrow(studentEntity.isPresent(), "Student not available with the id: " + studentId);

        Optional<SubjectEntity> subjectEntity = subjectRepository.findById(subjectId);
        validationUtils.checkIsResourceExistOrThrow(subjectEntity.isPresent(), "Subject not available with the id: " + subjectId);

        StudentEntity updatedStudentEntity = updateStudentWithSubjectId(studentEntity.get(), subjectEntity.get());

        return modelMapper.map(updatedStudentEntity, StudentDTO.class);
    }

    private StudentEntity updateStudentWithSubjectId(StudentEntity studentEntity, SubjectEntity subjectEntity) {
        studentEntity.getSubjects().add(subjectEntity);
        return studentRepository.save(studentEntity);
    }
}
