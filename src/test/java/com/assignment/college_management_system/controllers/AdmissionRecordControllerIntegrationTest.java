package com.assignment.college_management_system.controllers;

import com.assignment.college_management_system.dtos.AdmissionRecordDTO;
import com.assignment.college_management_system.entities.AdmissionRecordEntity;
import com.assignment.college_management_system.entities.StudentEntity;
import org.junit.jupiter.api.Test;

class AdmissionRecordControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    void testAssignAdmissionRecordToStudent_whenAdmissionAndStudentExistAndValid_thenSuccess() {
        AdmissionRecordEntity admissionRecord = admissionRecordRepository.save(createMockAdmissionRecord(15000.00));
        StudentEntity student = studentRepository.save(createMockStudent("Anvita"));

        webTestClient.put()
                .uri("/api/v1/admission-record/{admissionRecordId}/student/{studentId}", admissionRecord.getId(), student.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.id").isEqualTo(admissionRecord.getId())
                .jsonPath("$.data.fees").isEqualTo(admissionRecord.getFees())
                .jsonPath("$.data.student.id").isEqualTo(student.getId())
                .jsonPath("$.data.student.name").isEqualTo(student.getName());
    }

    @Test
    void testGetAdmissionRecordById_whenAdmissionExist_thenSuccess() {
        AdmissionRecordEntity admissionRecordEntity = admissionRecordRepository.save(createMockAdmissionRecord(15000.00));

        webTestClient.get()
                .uri("/api/v1/admission-record/{admissionRecordId}", admissionRecordEntity.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.id").isEqualTo(admissionRecordEntity.getId())
                .jsonPath("$.data.fees").isEqualTo(admissionRecordEntity.getFees());
    }

    @Test
    void testGetAdmissionRecordById_whenAdmissionDoesNotExist_thenFailure() {
        webTestClient.get()
                .uri("/api/v1/admission-record/{admissionRecordId}", 11L)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.error.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.error.message").isEqualTo("Admission record not available with the id: 11");
    }

    @Test
    void testSaveAdmissionRecord_whenAdmissionExistAndValid_thenSuccess() {
        AdmissionRecordDTO admissionRecordDTO = AdmissionRecordDTO.builder()
                .fees(20000.00)
                .build();

        webTestClient.post()
                .uri("/api/v1/admission-record")
                .bodyValue(admissionRecordDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.data.id").exists()
                .jsonPath("$.data.id").isNumber()
                .jsonPath("$.data.fees").isEqualTo(admissionRecordDTO.getFees());
    }
}
