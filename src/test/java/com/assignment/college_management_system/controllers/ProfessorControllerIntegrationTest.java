package com.assignment.college_management_system.controllers;

import com.assignment.college_management_system.dtos.ProfessorDTO;
import com.assignment.college_management_system.entities.ProfessorEntity;
import com.assignment.college_management_system.entities.StudentEntity;
import org.junit.jupiter.api.Test;

class ProfessorControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    void testAssignProfessorToStudent_whenProfessorExistAndStudentDoesNotExist_thenFailure() {
        ProfessorEntity professorEntity = professorRepository.save(createMockProfessor("Arpit"));

        webTestClient.put()
                .uri("/api/v1/professor/{professorId}/student/{studentId}", professorEntity.getId(), 102L)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.error.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.error.message").isEqualTo("Student not found with the id: 102");
    }

    @Test
    void testAssignProfessorToStudent_whenProfessorAndStudentExistAndValid_thenSuccess() {
        ProfessorEntity professorEntity = professorRepository.save(createMockProfessor("Arpit"));
        StudentEntity studentEntity = studentRepository.save(createMockStudent("Dhruva"));

        webTestClient.put()
                .uri("/api/v1/professor/{professorId}/student/{studentId}", professorEntity.getId(), studentEntity.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.id").isEqualTo(professorEntity.getId())
                .jsonPath("$.data.name").isEqualTo(professorEntity.getName())
                .jsonPath("$.data.students.length()").isEqualTo(1)
                .jsonPath("$.data.students[0].id").isEqualTo(studentEntity.getId())
                .jsonPath("$.data.students[0].name").isEqualTo(studentEntity.getName());
    }

    @Test
    void testSaveProfessor_whenProfessorDataValid_thenSuccess() {
        ProfessorDTO professorDTO = ProfessorDTO.builder()
                .name("Hemang")
                .build();
        webTestClient.post()
                .uri("/api/v1/professor")
                .bodyValue(professorDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.data.id").exists()
                .jsonPath("$.data.id").isNumber()
                .jsonPath("$.data.name").isEqualTo(professorDTO.getName());
    }

    @Test
    void testSaveProfessor_whenProfessorDataInValid_thenFailure() {
        ProfessorDTO professorDTO = ProfessorDTO.builder()
                .name("")
                .build();

        webTestClient.post()
                .uri("/api/v1/professor")
                .bodyValue(professorDTO)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.data").isEqualTo(null)
                .jsonPath("$.error.httpStatus").isEqualTo("BAD_REQUEST")
                .jsonPath("$.error.message").isEqualTo("Input validation failed.")
                .jsonPath("$.error.errorList.length()").isEqualTo(1)
                .jsonPath("$.error.errorList[0]").isEqualTo("Professor name is required.")
        ;
    }

    @Test
    void testGetProfessorById_whenProfessorExist_thenSuccess() {
        ProfessorEntity professor = professorRepository.save(createMockProfessor("Arpit"));

        webTestClient.get()
                .uri("/api/v1/professor//{professorId}", professor.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.id").isEqualTo(professor.getId())
                .jsonPath("$.data.name").isEqualTo(professor.getName());
    }

    @Test
    void testGetProfessorById_whenProfessorDoesNotExistAndValid_thenFailure() {
        webTestClient.get()
                .uri("/api/v1/professor/{professorId}", 101L)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.error.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.error.message").isEqualTo("Professor not found with the id: 101");
    }

    @Test
    void testGetProfessorById_whenUriIsInvalid_thenThrowException() {
        webTestClient.get()
                .uri("/api/v1/professordfd/{professorId}", 10L)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.error.httpStatus").isEqualTo("INTERNAL_SERVER_ERROR")
                .jsonPath("$.error.message").isEqualTo("No static resource api/v1/professordfd/10.");
    }
}
