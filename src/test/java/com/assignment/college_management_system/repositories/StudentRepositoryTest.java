package com.assignment.college_management_system.repositories;

import com.assignment.college_management_system.JpaTestContainerConfiguration;
import com.assignment.college_management_system.entities.StudentEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Import(JpaTestContainerConfiguration.class)
@AutoConfigureTestDatabase
@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    StudentEntity mockedStudentEntity;

    @BeforeEach
    void setup(){
        mockedStudentEntity = StudentEntity.builder()
                .name("Sathish")
                .build();

        studentRepository.deleteAll();
    }

    @Test
    void testFindByName_whenStudentExist_thenReturnStudent() {
        studentRepository.save(mockedStudentEntity);

        Optional<StudentEntity> studentEntity = studentRepository.findByName(mockedStudentEntity.getName());

        assertThat(studentEntity.get().getName()).isEqualTo(mockedStudentEntity.getName());
        assertThat(studentEntity.get().getId()).isEqualTo(mockedStudentEntity.getId());
    }

    @Test
    void testFindByName_whenStudentNotExist_thenReturnEmpty() {
        Optional<StudentEntity> studentEntity = studentRepository.findByName(mockedStudentEntity.getName());

        assertThat(studentEntity).isEmpty();
    }
}
