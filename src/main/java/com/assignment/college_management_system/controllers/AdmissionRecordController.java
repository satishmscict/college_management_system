package com.assignment.college_management_system.controllers;

import com.assignment.college_management_system.entities.AdmissionRecordEntity;
import com.assignment.college_management_system.entities.StudentEntity;
import com.assignment.college_management_system.repositories.AdmissionRecordRepository;
import com.assignment.college_management_system.repositories.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admission-record/v1")
public class AdmissionRecordController {

    AdmissionRecordRepository admissionRecordRepository;
    StudentRepository studentRepository;

    public AdmissionRecordController(AdmissionRecordRepository admissionRecordRepository, StudentRepository studentRepository) {
        this.admissionRecordRepository = admissionRecordRepository;
        this.studentRepository = studentRepository;
    }

    @PostMapping(path = "/{admissionRecordId}/student/{studentId}")
    public ResponseEntity<AdmissionRecordEntity> assignAdmissionRecordToStudent(
            @PathVariable Long admissionRecordId,
            @PathVariable Long studentId
    ) throws Exception {
        StudentEntity studentEntity = studentRepository.findById(studentId).orElse(null);
        AdmissionRecordEntity admissionRecordEntity = admissionRecordRepository.findById(admissionRecordId).orElse(null);

        if (studentEntity == null || admissionRecordEntity == null) {
            throw new Exception("Student or Admission record data not available.");
        }

        admissionRecordEntity.setStudentEntity(studentEntity);
        AdmissionRecordEntity updatedAdmissionRecordEntity = admissionRecordRepository.save(admissionRecordEntity);

        return new ResponseEntity<>(updatedAdmissionRecordEntity, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<AdmissionRecordEntity> saveAdmissionRecord(@RequestBody AdmissionRecordEntity admissionRecordEntity) {
        return ResponseEntity.ok(admissionRecordRepository.save(admissionRecordEntity));
    }
}
