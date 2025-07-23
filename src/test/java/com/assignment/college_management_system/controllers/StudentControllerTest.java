package com.assignment.college_management_system.controllers;

import com.assignment.college_management_system.JpaTestContainerConfiguration;
import com.assignment.college_management_system.dtos.StudentDTO;
import com.assignment.college_management_system.entities.StudentEntity;
import com.assignment.college_management_system.entities.SubjectEntity;
import com.assignment.college_management_system.repositories.StudentRepository;
import com.assignment.college_management_system.repositories.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient(timeout = "100000")
@Import(JpaTestContainerConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    private StudentEntity mockedStudentEntity;

    private SubjectEntity mockedsSubjectEntity;

    private StudentDTO mockedStudentDTO;

    @BeforeEach
    void setup() {
        mockedStudentEntity = StudentEntity.builder()
                // .id(1L)
                .name("Dhruva")
                .build();
        mockedStudentDTO = StudentDTO.builder()
                //.id(1L)
                .name("Dhruva")
                .build();

        mockedsSubjectEntity = SubjectEntity.builder()
                .name("DBMS")
                .build();

        studentRepository.deleteAll();
        subjectRepository.deleteAll();
    }

    @Test
    void getAllStudents() {
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
                .uri("/api/v1/students/studentName/{studentName}", "abcd")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody();
    }

    @Test
    void testSaveStudent_whenStudentDataIsValid_thenSuccess() {

//        webTestClient.post()
//                .uri("/api/v1/students")
//                .bodyValue(mockedStudentDTO)
//                .exchange()
//                .expectStatus().isCreated()
//                .expectBody(new ParameterizedTypeReference<ApiResponse<StudentDTO>>() {})
//                .consumeWith(response -> {
//                    ApiResponse<StudentDTO> apiResponse = response.getResponseBody();
//                    assertNotNull(apiResponse);
//                    assertEquals(mockedStudentEntity.getName(), apiResponse.getData().getName());
//                    assertEquals(mockedStudentEntity.getId(), apiResponse.getData().getId());
//                });

        webTestClient.post()
                .uri("/api/v1/students")
                .bodyValue(mockedStudentDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.data.id").isEqualTo(1L)
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

