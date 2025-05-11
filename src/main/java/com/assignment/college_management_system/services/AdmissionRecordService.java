package com.assignment.college_management_system.services;

import com.assignment.college_management_system.entities.AdmissionRecordEntity;
import com.assignment.college_management_system.entities.StudentEntity;
import com.assignment.college_management_system.repositories.AdmissionRecordRepository;
import com.assignment.college_management_system.repositories.StudentRepository;
import com.assignment.college_management_system.utils.ValidationUtils;
import org.springframework.stereotype.Service;

@Service
public class AdmissionRecordService {

    private final AdmissionRecordRepository admissionRecordRepository;
    private final StudentRepository studentRepository;
    private final ValidationUtils validationUtils;

    public AdmissionRecordService(
            AdmissionRecordRepository admissionRecordRepository,
            StudentRepository studentRepository,
            ValidationUtils validationUtils
    ) {
        this.admissionRecordRepository = admissionRecordRepository;
        this.studentRepository = studentRepository;
        this.validationUtils = validationUtils;
    }

    public AdmissionRecordEntity assignAdmissionRecordToStudent(Long admissionRecordId, Long studentId) {
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

        admissionRecordEntity.setStudentEntity(studentEntity);

        return admissionRecordRepository.save(admissionRecordEntity);
    }

    public AdmissionRecordEntity saveAdmissionRecord(AdmissionRecordEntity admissionRecordEntity) {
        return admissionRecordRepository.save(admissionRecordEntity);
    }

    public AdmissionRecordEntity getAdmissionRecordById(Long admissionRecordId) {
        validationUtils.checkIsResourceExistOrThrow(
                admissionRecordRepository.existsById(admissionRecordId),
                "Admission record not available with the id: " + admissionRecordId
        );

        return admissionRecordRepository.findById(admissionRecordId).orElseThrow();
    }
}
