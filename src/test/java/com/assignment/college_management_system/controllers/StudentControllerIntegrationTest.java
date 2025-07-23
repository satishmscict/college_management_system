package com.assignment.college_management_system.controllers;

import com.assignment.college_management_system.dtos.StudentDTO;
import com.assignment.college_management_system.entities.StudentEntity;
import com.assignment.college_management_system.entities.SubjectEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentControllerIntegrationTest extends BaseIntegrationTest {

    private StudentEntity mockedStudentEntity;

    private SubjectEntity mockedsSubjectEntity;

    private StudentDTO mockedStudentDTO;

    @BeforeEach
    void setup() {
        mockedStudentEntity = createMockStudent("Dhruva");

        mockedStudentDTO = StudentDTO.builder()
                .name("Dhruva")
                .build();

        mockedsSubjectEntity = createMockSubject("DBMS");
    }

    @Test
    void getAllStudents() {
        StudentEntity studentEntity = studentRepository.save(mockedStudentEntity);

        webTestClient.get()
                .uri("/api/v1/students")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.length()").isEqualTo(1)
                .jsonPath("$.data[0].id").isEqualTo(studentEntity.getId().intValue())
                .jsonPath("$.data[0].name").isEqualTo(studentEntity.getName());

        // Hit API and print the result.
        //        webTestClient.get()
//                .uri("/api/v1/students")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(String.class)
//                .consumeWith(response -> {
//                    System.out.println("Response: " + response.getResponseBody());
//                });

    }

    @Test
    void testGetStudentById_whenStudentIsExist_thenReturnSuccess() {
        StudentEntity studentEntity = studentRepository.save(mockedStudentEntity);

        webTestClient.get()
                .uri("/api/v1/students/studentId/{studentId}", studentEntity.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.id").isEqualTo(studentEntity.getId())
                .jsonPath("$.data.name").isEqualTo(studentEntity.getName());
//                .value(studentDTO -> {
//                   /// StudentDTO studentDTO = (StudentDTO) apiResponse. .getData();
//                    assertThat(studentDTO.getId()).isEqualTo(studentEntity.getId());
//                    assertThat(studentDTO.getName()).isEqualTo(studentEntity.getName());
//                });
    }

    @Test
    void testGetStudentById_whenStudentDoesNotExist_thenReturnException() {
        webTestClient.get()
                .uri("/api/v1/students/studentId/{studentId}", 10L)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody();
    }

    @Test
    void testGetStudentByName_whenStudentExist_thenSuccess() {
        StudentEntity studentEntity = studentRepository.save(mockedStudentEntity);

        webTestClient.get()
                .uri("/api/v1/students/studentName/{studentName}", studentEntity.getName())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.name").isEqualTo(studentEntity.getName());
    }

    @Test
    void testGetStudentByName_whenStudentNotExist_thenFail() {
        webTestClient.get()
                .uri("/api/v1/students/studentName/{studentName}", "unknown")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody();
    }

    @Test
    void testSaveStudent_whenStudentDataIsValid_thenSuccess() {
        webTestClient.post()
                .uri("/api/v1/students")
                .bodyValue(mockedStudentDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.data.id").exists()
                .jsonPath("$.data.id").isNumber()
                .jsonPath("$.data.name").isEqualTo(mockedStudentEntity.getName());
    }

    @Test
    void testAssignStudentToSubject_whenStudentAndSubjectDataIsValid_thenSuccess() {
        StudentEntity studentEntity = studentRepository.save(mockedStudentEntity);
        SubjectEntity subjectEntity = subjectRepository.save(mockedsSubjectEntity);

        webTestClient.put()
                .uri("/api/v1/students/studentId/{studentId}/subjectId/{subjectId}", studentEntity.getId(), subjectEntity.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.id").isEqualTo(studentEntity.getId())
                .jsonPath("$.data.name").isEqualTo("Dhruva")
                .jsonPath("$.data.subjects[0].id").isEqualTo(subjectEntity.getId())
                .jsonPath("$.data.subjects[0].name").isEqualTo("DBMS");

        // Added this data to understand mapping.
        String actualResponse = """
                {
                    "data": {
                        "id": 1,
                        "name": "Dhruva",
                        "subjects": [
                            {
                                "id": 1,
                                "name": "DBMS"
                            }
                        ]
                    },
                    "error": null,
                    "localDateTime": "23-07-2025 10:06:31"
                }""";
    }

    @Test
    void testAssignStudentToSubject_whenStudentExistAndSubjectDataNotExist_thenFailure() {
        StudentEntity studentEntity = studentRepository.save(mockedStudentEntity);

        webTestClient.put()
                .uri("/api/v1/students/studentId/{studentId}/subjectId/{subjectId}", studentEntity.getId(), 10L)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.error.message").isEqualTo("Subject not available with the id: 10")
                .jsonPath("$.error.httpStatus").isEqualTo("NOT_FOUND");

        // Added this data to understand mapping.
        String actualResponse = """
                {
                    "data": null,
                    "error": {
                        "httpStatus": "NOT_FOUND",
                        "message": "Subject not available with the id: 10",
                        "errorList": null
                    },
                    "localDateTime": "23-07-2025 08:42:53"
                }""";
    }
}

