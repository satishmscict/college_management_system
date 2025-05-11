package com.assignment.college_management_system.services;

import com.assignment.college_management_system.entities.AdmissionRecordEntity;
import com.assignment.college_management_system.entities.StudentEntity;
import com.assignment.college_management_system.exceptions.ResourceNotFoundException;
import com.assignment.college_management_system.repositories.AdmissionRecordRepository;
import com.assignment.college_management_system.repositories.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class AdmissionRecordService {

    private final AdmissionRecordRepository admissionRecordRepository;
    private final StudentRepository studentRepository;

    public AdmissionRecordService(AdmissionRecordRepository admissionRecordRepository, StudentRepository studentRepository) {
        this.admissionRecordRepository = admissionRecordRepository;
        this.studentRepository = studentRepository;
    }

    public AdmissionRecordEntity assignAdmissionRecordToStudent(Long admissionRecordId, Long studentId) {
        StudentEntity studentEntity = studentRepository.findById(studentId).orElse(null);
        checkIsEntityExistOrThrow(studentEntity == null, "Student not available with the id: " + studentId);

        AdmissionRecordEntity admissionRecordEntity = admissionRecordRepository.findById(admissionRecordId).orElse(null);
        checkIsEntityExistOrThrow(admissionRecordEntity == null, "Admission record not available with the id: " + studentId);

        admissionRecordEntity.setStudentEntity(studentEntity);

        return admissionRecordRepository.save(admissionRecordEntity);
    }

    public AdmissionRecordEntity saveAdmissionRecord(AdmissionRecordEntity admissionRecordEntity) {
        return admissionRecordRepository.save(admissionRecordEntity);
    }

    public AdmissionRecordEntity getAdmissionRecordById(Long admissionRecordId) {
        checkIsEntityExistOrThrow(!admissionRecordRepository.existsById(admissionRecordId), "Admission record not available with the id: " + admissionRecordId);

        return admissionRecordRepository.findById(admissionRecordId).orElseThrow();
    }

    private void checkIsEntityExistOrThrow(Boolean isRecordNotExist, String message) {
        if (isRecordNotExist) {
            throw new ResourceNotFoundException(message);
        }
    }
}
