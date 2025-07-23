package com.assignment.college_management_system.services;

import com.assignment.college_management_system.dtos.AdmissionRecordDTO;

public interface AdmissionRecordService {

    AdmissionRecordDTO assignAdmissionRecordToStudent(Long admissionRecordId, Long studentId);

    AdmissionRecordDTO getAdmissionRecordById(Long admissionRecordId);

    AdmissionRecordDTO saveAdmissionRecord(AdmissionRecordDTO admissionRecordDTO);
}
