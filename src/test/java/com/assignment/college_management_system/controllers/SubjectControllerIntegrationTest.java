package com.assignment.college_management_system.controllers;

import com.assignment.college_management_system.dtos.SubjectDTO;
import com.assignment.college_management_system.entities.ProfessorEntity;
import com.assignment.college_management_system.entities.SubjectEntity;
import org.junit.jupiter.api.Test;

class SubjectControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    void testSaveSubject_whenSubjectDataIsValid_thenSuccess() {
        SubjectDTO subjectDTO = new SubjectDTO(null, "Advance Java");

        webTestClient.post()
                .uri("/api/v1/subjects")
                .bodyValue(subjectDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.data.id").exists()
                .jsonPath("$.data.id").isNumber()
                .jsonPath("$.data.name").isEqualTo(subjectDTO.getName());
    }

    @Test
    void testGetSubjectById_whenSubjectExist_thenSuccess() {
        SubjectEntity subjectEntity = subjectRepository.save(createMockSubject("Java"));

        webTestClient.get()
                .uri("/api/v1/subjects/{subjectId}", subjectEntity.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.id").isNumber()
                .jsonPath("$.data.id").isEqualTo(subjectEntity.getId())
                .jsonPath("$.data.name").isEqualTo(subjectEntity.getName());
    }

    @Test
    void testGetSubjectById_whenSubjectDoesNotExist_thenFailure() {
        webTestClient.get()
                .uri("/api/v1/subjects/{subjectId}", 10L)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.error.message").isEqualTo("Subject not available with the id: 10");
    }

    @Test
    void testAssignSubjectToProfessor_whenSubjectAndProfessorExist_thenSuccess() {
        SubjectEntity subjectEntity = subjectRepository.save(createMockSubject("Java"));
        ProfessorEntity professor = professorRepository.save(createMockProfessor("Arpit"));

        webTestClient.put()
                .uri("/api/v1/subjects/{subjectId}/professor/{professorId}", subjectEntity.getId(), professor.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.id").isEqualTo(subjectEntity.getId())
                .jsonPath("$.data.name").isEqualTo(subjectEntity.getName());
    }

    @Test
    void testAssignSubjectToProfessor_whenSubjectExistAndProfessorDoesNotExist_thenFailure() {
        SubjectEntity subjectEntity = subjectRepository.save(createMockSubject("Java"));

        webTestClient.put()
                .uri("/api/v1/subjects/{subjectId}/professor/{professorId}", subjectEntity.getId(), 10L)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.error.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.error.message").isEqualTo("Professor not available with the id: 10");
    }

    @Test
    void testAssignSubjectToProfessor_whenSubjectDoestNotExistAndProfessorExist_thenFailure() {
        ProfessorEntity professor = professorRepository.save(createMockProfessor("Arpit"));

        webTestClient.put()
                .uri("/api/v1/subjects/{subjectId}/professor/{professorId}", 10L, professor.getId())
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.error.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.error.message").isEqualTo("Subject not available with the id: 10");
    }
}
