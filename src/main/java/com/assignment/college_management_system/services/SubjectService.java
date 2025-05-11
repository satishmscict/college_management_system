package com.assignment.college_management_system.services;

import com.assignment.college_management_system.dtos.SubjectDTO;
import com.assignment.college_management_system.entities.ProfessorEntity;
import com.assignment.college_management_system.entities.SubjectEntity;
import com.assignment.college_management_system.repositories.ProfessorRepository;
import com.assignment.college_management_system.repositories.SubjectRepository;
import com.assignment.college_management_system.utils.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectService {

    private final ModelMapper modelMapper;
    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;
    private final ValidationUtils validationUtils;

    public SubjectService(
            ModelMapper modelMapper,
            ProfessorRepository professorRepository,
            SubjectRepository subjectRepository,
            ValidationUtils validationUtils
    ) {
        this.modelMapper = modelMapper;
        this.professorRepository = professorRepository;
        this.subjectRepository = subjectRepository;
        this.validationUtils = validationUtils;
    }

    public SubjectDTO saveSubject(SubjectDTO subjectDTO) {
        SubjectEntity subjectEntity = modelMapper.map(subjectDTO, SubjectEntity.class);
        SubjectEntity savedSubjectEntity = subjectRepository.save(subjectEntity);

        return modelMapper.map(savedSubjectEntity, SubjectDTO.class);
    }

    public SubjectDTO getSubjectById(Long subjectId) {
        validationUtils.checkIsResourceExistOrThrow(subjectRepository.existsById(subjectId), "Subject not available with the id: " + subjectId);

        Optional<SubjectEntity> subjectEntity = subjectRepository.findById(subjectId);
        return modelMapper.map(subjectEntity, SubjectDTO.class);
    }

    public SubjectDTO assignSubjectToProfessor(Long subjectId, Long professorId) {
        Optional<SubjectEntity> subjectEntity = subjectRepository.findById(subjectId);
        validationUtils.checkIsResourceExistOrThrow(subjectEntity.isPresent(), "Subject not available with the id: " + subjectId);

        Optional<ProfessorEntity> professorEntity = professorRepository.findById(professorId);
        validationUtils.checkIsResourceExistOrThrow(professorEntity.isPresent(), "Professor not available with the id: " + professorId);

        subjectEntity.get().setProfessor(professorEntity.get());
        SubjectEntity updateSubjectEntity = subjectRepository.save(subjectEntity.get());

        return modelMapper.map(updateSubjectEntity, SubjectDTO.class);
    }
}
