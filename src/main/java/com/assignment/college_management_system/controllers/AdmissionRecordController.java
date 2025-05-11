package com.assignment.college_management_system.controllers;

import com.assignment.college_management_system.entities.AdmissionRecordEntity;
import com.assignment.college_management_system.services.AdmissionRecordService;
import com.assignment.college_management_system.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admission-record/v1")
public class AdmissionRecordController {

    AdmissionRecordService admissionRecordService;
    StudentService studentService;

    public AdmissionRecordController(AdmissionRecordService admissionRecordService, StudentService studentService) {
        this.admissionRecordService = admissionRecordService;
        this.studentService = studentService;
    }


    @GetMapping(path = "/{admissionRecordId}")
    public ResponseEntity<AdmissionRecordEntity> getAdmissionRecordById(@PathVariable Long admissionRecordId) {
        return new ResponseEntity<>(admissionRecordService.getAdmissionRecordById(admissionRecordId), HttpStatus.OK);
    }

    @PutMapping(path = "/{admissionRecordId}/student/{studentId}")
    public ResponseEntity<AdmissionRecordEntity> assignAdmissionRecordToStudent(
            @PathVariable Long admissionRecordId,
            @PathVariable Long studentId
    ) {

        AdmissionRecordEntity admissionRecordEntity = admissionRecordService.assignAdmissionRecordToStudent(admissionRecordId, studentId);

        return new ResponseEntity<>(admissionRecordEntity, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AdmissionRecordEntity> saveAdmissionRecord(@RequestBody AdmissionRecordEntity admissionRecordEntity) {
        AdmissionRecordEntity admissionRecord = admissionRecordService.saveAdmissionRecord(admissionRecordEntity);
        return ResponseEntity.ok(admissionRecord);
    }
}
