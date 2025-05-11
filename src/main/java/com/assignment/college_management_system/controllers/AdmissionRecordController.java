package com.assignment.college_management_system.controllers;

import com.assignment.college_management_system.dtos.AdmissionRecordDTO;
import com.assignment.college_management_system.services.AdmissionRecordService;
import com.assignment.college_management_system.services.StudentService;
import jakarta.validation.Valid;
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

    @PutMapping(path = "/{admissionRecordId}/student/{studentId}")
    public ResponseEntity<AdmissionRecordDTO> assignAdmissionRecordToStudent(
            @PathVariable Long admissionRecordId,
            @PathVariable Long studentId
    ) {
        return ResponseEntity.ok(admissionRecordService.assignAdmissionRecordToStudent(admissionRecordId, studentId));
    }

    @GetMapping(path = "/{admissionRecordId}")
    public ResponseEntity<AdmissionRecordDTO> getAdmissionRecordById(@PathVariable Long admissionRecordId) {
        return ResponseEntity.ok(admissionRecordService.getAdmissionRecordById(admissionRecordId));
    }

    @PostMapping
    public ResponseEntity<AdmissionRecordDTO> saveAdmissionRecord(@RequestBody @Valid AdmissionRecordDTO admissionRecordDTO) {
        return ResponseEntity.ok(admissionRecordService.saveAdmissionRecord(admissionRecordDTO));
    }
}
