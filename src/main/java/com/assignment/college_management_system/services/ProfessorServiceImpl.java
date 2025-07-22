package com.assignment.college_management_system.services;

import com.assignment.college_management_system.dtos.ProfessorDTO;
import com.assignment.college_management_system.entities.ProfessorEntity;
import com.assignment.college_management_system.entities.StudentEntity;
import com.assignment.college_management_system.repositories.ProfessorRepository;
import com.assignment.college_management_system.repositories.StudentRepository;
import com.assignment.college_management_system.utils.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    private final ModelMapper modelMapper;
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;
    private final ValidationUtils validationUtils;

    public ProfessorServiceImpl(
            ModelMapper modelMapper,
            ProfessorRepository professorRepository,
            StudentRepository studentRepository,
            ValidationUtils validationUtils
    ) {
        this.modelMapper = modelMapper;
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
        this.validationUtils = validationUtils;
    }

    @Override
    public ProfessorDTO assignProfessorToStudent(Long professorId, Long studentId) {
        Optional<ProfessorEntity> professorEntity = professorRepository.findById(professorId);
        validationUtils.checkIsResourceExistOrThrow(professorEntity.isPresent(), "Professor not found with the id: " + professorId);

        Optional<StudentEntity> studentEntity = studentRepository.findById(studentId);
        validationUtils.checkIsResourceExistOrThrow(studentEntity.isPresent(), "Student not found with the id: " + studentId);

       return updateProfessorWithStudent(professorEntity.get(), studentEntity.get());
    }

    @Override
    public ProfessorDTO getProfessorById(Long professorId) {
        Optional<ProfessorEntity> professorEntity = professorRepository.findById(professorId);
        validationUtils.checkIsResourceExistOrThrow(professorEntity.isPresent(), "Professor not found with the id: " + professorId);

        return modelMapper.map(professorEntity, ProfessorDTO.class);
    }

    @Override
    public ProfessorDTO saveProfessor(ProfessorDTO professorDTO) {
        ProfessorEntity professorEntity = modelMapper.map(professorDTO, ProfessorEntity.class);
        ProfessorEntity savedProfessorEntity = professorRepository.save(professorEntity);

        return modelMapper.map(savedProfessorEntity, ProfessorDTO.class);
    }

    private ProfessorDTO updateProfessorWithStudent(ProfessorEntity professorEntity, StudentEntity studentEntity) {
        professorEntity.getStudents().add(studentEntity);
        ProfessorEntity savedProfessorEntity = professorRepository.save(professorEntity);

        return modelMapper.map(savedProfessorEntity, ProfessorDTO.class);
    }
}
