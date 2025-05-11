package com.assignment.college_management_system.services;

import com.assignment.college_management_system.dtos.ProfessorDTO;
import com.assignment.college_management_system.entities.ProfessorEntity;
import com.assignment.college_management_system.repositories.ProfessorRepository;
import com.assignment.college_management_system.utils.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfessorService {

    private final ModelMapper modelMapper;
    private final ProfessorRepository professorRepository;
    private final ValidationUtils validationUtils;

    public ProfessorService(ModelMapper modelMapper, ProfessorRepository professorRepository, ValidationUtils validationUtils) {
        this.modelMapper = modelMapper;
        this.professorRepository = professorRepository;
        this.validationUtils = validationUtils;
    }

    public ProfessorDTO saveProfessor(ProfessorDTO professorDTO) {
        ProfessorEntity professorEntity = modelMapper.map(professorDTO, ProfessorEntity.class);
        ProfessorEntity savedProfessorEntity = professorRepository.save(professorEntity);

        return modelMapper.map(savedProfessorEntity, ProfessorDTO.class);
    }

    public ProfessorDTO getProfessorById(Long professorId) {
        validationUtils.checkIsResourceExistOrThrow(professorRepository.existsById(professorId), "Progessor not found with the id: " + professorId);

        Optional<ProfessorEntity> professorEntity = professorRepository.findById(professorId);
        return modelMapper.map(professorEntity, ProfessorDTO.class);
    }
}
