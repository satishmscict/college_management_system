package com.assignment.college_management_system.services;

import com.assignment.college_management_system.dtos.AdmissionRecordDTO;

public interface AdmissionRecordService {

    public AdmissionRecordDTO assignAdmissionRecordToStudent(Long admissionRecordId, Long studentId);

    public AdmissionRecordDTO getAdmissionRecordById(Long admissionRecordId);

    public AdmissionRecordDTO saveAdmissionRecord(AdmissionRecordDTO admissionRecordDTO);
}
