package com.assignment.college_management_system.controllers;

import com.assignment.college_management_system.JpaTestContainerConfiguration;
import com.assignment.college_management_system.entities.AdmissionRecordEntity;
import com.assignment.college_management_system.entities.ProfessorEntity;
import com.assignment.college_management_system.entities.StudentEntity;
import com.assignment.college_management_system.entities.SubjectEntity;
import com.assignment.college_management_system.repositories.AdmissionRecordRepository;
import com.assignment.college_management_system.repositories.ProfessorRepository;
import com.assignment.college_management_system.repositories.StudentRepository;
import com.assignment.college_management_system.repositories.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;

@Import(JpaTestContainerConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "100000")
public class BaseIntegrationTest {

    @Autowired
    protected AdmissionRecordRepository admissionRecordRepository;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected ProfessorRepository professorRepository;

    @Autowired
    protected StudentRepository studentRepository;

    @Autowired
    protected SubjectRepository subjectRepository;

    @Autowired
    protected WebTestClient webTestClient;

    @BeforeEach
    void cleanDatabase() {
        // 1. Delete many-to-many or dependent mappings first
        jdbcTemplate.update("DELETE FROM student_subject_mapping");
        jdbcTemplate.update("DELETE FROM professor_student_mapping");

        // 2. Delete one-to-many dependent tables next
        admissionRecordRepository.deleteAll();

        // 3. Delete main domain tables
        studentRepository.deleteAll();
        subjectRepository.deleteAll();
        professorRepository.deleteAll();
    }

    protected AdmissionRecordEntity createMockAdmissionRecord(Double fees) {
        return admissionRecordRepository.save(
                AdmissionRecordEntity.builder()
                        .fees(fees)
                        .build()
        );
    }

    protected ProfessorEntity createMockProfessor(String professorName) {
        return professorRepository.save(
                ProfessorEntity.builder()
                        .name(professorName)
                        .build()
        );
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
