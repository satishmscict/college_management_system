package com.assignment.college_management_system.services;

import com.assignment.college_management_system.entities.AdmissionRecordEntity;
import com.assignment.college_management_system.entities.StudentEntity;
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

    public AdmissionRecordEntity assignAdmissionRecordToStudent(Long admissionRecordId, Long studentId) throws Exception {
        StudentEntity studentEntity = studentRepository.findById(studentId).orElse(null);
        AdmissionRecordEntity admissionRecordEntity = admissionRecordRepository.findById(admissionRecordId).orElse(null);

        if (studentEntity == null || admissionRecordEntity == null) {
            throw new Exception("Student or Admission record data not available.");
        }

        admissionRecordEntity.setStudentEntity(studentEntity);
        return admissionRecordRepository.save(admissionRecordEntity);
    }

    public AdmissionRecordEntity saveAdmissionRecord(AdmissionRecordEntity admissionRecordEntity) {
        return admissionRecordRepository.save(admissionRecordEntity);
    }
}
