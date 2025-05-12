package com.assignment.college_management_system.services;

import com.assignment.college_management_system.dtos.AdmissionRecordDTO;
import com.assignment.college_management_system.entities.AdmissionRecordEntity;
import com.assignment.college_management_system.entities.StudentEntity;
import com.assignment.college_management_system.repositories.AdmissionRecordRepository;
import com.assignment.college_management_system.repositories.StudentRepository;
import com.assignment.college_management_system.utils.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdmissionRecordService {

    private final AdmissionRecordRepository admissionRecordRepository;
    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;
    private final ValidationUtils validationUtils;

    public AdmissionRecordService(
            AdmissionRecordRepository admissionRecordRepository,
            ModelMapper modelMapper,
            StudentRepository studentRepository,
            ValidationUtils validationUtils
    ) {
        this.admissionRecordRepository = admissionRecordRepository;
        this.modelMapper = modelMapper;
        this.studentRepository = studentRepository;
        this.validationUtils = validationUtils;
    }

    public AdmissionRecordDTO assignAdmissionRecordToStudent(Long admissionRecordId, Long studentId) {
        StudentEntity studentEntity = studentRepository.findById(studentId).orElse(null);
        validationUtils.checkIsResourceExistOrThrow(
                studentEntity != null,
                "Student not available with the id: " + studentId
        );

        AdmissionRecordEntity admissionRecordEntity = admissionRecordRepository.findById(admissionRecordId).orElse(null);
        validationUtils.checkIsResourceExistOrThrow(
                admissionRecordEntity != null,
                "Admission record not available with the id: " + admissionRecordId
        );

        admissionRecordEntity.setStudent(studentEntity);

        AdmissionRecordEntity updatedAdmissionRecordEntity = admissionRecordRepository.save(admissionRecordEntity);

        return modelMapper.map(updatedAdmissionRecordEntity, AdmissionRecordDTO.class);
    }

    public AdmissionRecordDTO saveAdmissionRecord(AdmissionRecordDTO admissionRecordDTO) {

        AdmissionRecordEntity admissionRecordEntity = modelMapper.map(admissionRecordDTO, AdmissionRecordEntity.class);
        AdmissionRecordEntity updateAdmissionRecordEntity = admissionRecordRepository.save(admissionRecordEntity);

        return modelMapper.map(updateAdmissionRecordEntity, AdmissionRecordDTO.class);
    }

    public AdmissionRecordDTO getAdmissionRecordById(Long admissionRecordId) {
        validationUtils.checkIsResourceExistOrThrow(
                admissionRecordRepository.existsById(admissionRecordId),
                "Admission record not available with the id: " + admissionRecordId
        );

        Optional<AdmissionRecordEntity> admissionRecordEntity = admissionRecordRepository.findById(admissionRecordId);

        return modelMapper.map(admissionRecordEntity, AdmissionRecordDTO.class);
    }
}
