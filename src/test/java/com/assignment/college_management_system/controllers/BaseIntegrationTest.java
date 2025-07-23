package com.assignment.college_management_system.controllers;

import com.assignment.college_management_system.JpaTestContainerConfiguration;
import com.assignment.college_management_system.entities.StudentEntity;
import com.assignment.college_management_system.entities.SubjectEntity;
import com.assignment.college_management_system.repositories.StudentRepository;
import com.assignment.college_management_system.repositories.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@Import(JpaTestContainerConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "100000")
public class BaseIntegrationTest {

    @Autowired
    protected StudentRepository studentRepository;

    @Autowired
    protected SubjectRepository subjectRepository;

    @Autowired
    protected WebTestClient webTestClient;

    @BeforeEach
    void cleanDatabase() {
        studentRepository.deleteAll();
        subjectRepository.deleteAll();
    }

    protected StudentEntity createMockStudent(String name) {
        return studentRepository.save(
                StudentEntity.builder()
                        .name(name)
                        .build()
        );
    }

    protected SubjectEntity createMockSubject(String subjectName) {
        return subjectRepository.save(
                SubjectEntity.builder()
                        .name(subjectName)
                        .build()
        );
    }
}
