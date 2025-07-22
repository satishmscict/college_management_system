package com.assignment.college_management_system.services;

import com.assignment.college_management_system.JpaTestContainerConfiguration;
import com.assignment.college_management_system.dtos.StudentDTO;
import com.assignment.college_management_system.dtos.SubjectDTO;
import com.assignment.college_management_system.entities.StudentEntity;
import com.assignment.college_management_system.entities.SubjectEntity;
import com.assignment.college_management_system.exceptions.ResourceNotFoundException;
import com.assignment.college_management_system.repositories.StudentRepository;
import com.assignment.college_management_system.repositories.SubjectRepository;
import com.assignment.college_management_system.utils.ValidationUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Import(JpaTestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository mockedStudentRepository;

    @Mock
    private SubjectRepository mockedSubjectRepository;

    @Spy
    private ModelMapper mockedModelMapper;

    @Spy
    private ValidationUtils mockedValidationUtils;

    @InjectMocks
    private StudentServiceImpl studentService;

    private StudentEntity mockedStudentEntity;
    private SubjectEntity mockedSubjectEntity;

    private StudentDTO mockedStudentDTO;
    private SubjectDTO mockedSubjectDTO;

    @BeforeEach
    void setUp() {
        mockedSubjectEntity = SubjectEntity.builder()
                .id(1L)
                .name("DBMS")
                .build();

        mockedStudentEntity = StudentEntity.builder()
                .id(1L)
                .name("Dhruva")
                .build();


        mockedStudentDTO = mockedModelMapper.map(mockedStudentEntity, StudentDTO.class);
        mockedSubjectDTO = mockedModelMapper.map(mockedSubjectEntity, SubjectDTO.class);

        mockedStudentRepository.deleteAll();
    }

    @Test
    void testAssignStudentToSubject_whenStudentAndSubjectExist_thenSaveAndReturnStudent() {
        // Arrange
        StudentEntity mockedStudent = mock(StudentEntity.class);
        SubjectEntity mockedSubject = mock(SubjectEntity.class);

        when(mockedSubjectRepository.findById(anyLong())).thenReturn(Optional.of(mockedSubject));

        // Set up a mutable collection for subjects
        Set<SubjectEntity> subjects = new HashSet<>();
        when(mockedStudent.getSubjects()).thenReturn(subjects);

        when(mockedStudentRepository.findById(anyLong())).thenReturn(Optional.of(mockedStudent));
        when(mockedStudentRepository.save(any(StudentEntity.class))).thenReturn(mockedStudent);

        when(mockedStudent.getName()).thenReturn("Dhruva");

        StudentDTO studentDTO = studentService.assignStudentToSubject(1L, 1L);

        assertThat(studentDTO).isNotNull();
        assertThat(studentDTO.getName()).isEqualTo("Dhruva");
        assertThat(studentDTO.getSubjects()).isNotNull();
        assertThat(studentDTO.getSubjects().isEmpty()).isFalse();
        assertThat(studentDTO.getSubjects().contains(mockedSubjectDTO));
    }

    @Test
    void testFindStudentByName_whenStudentExist_thenReturnStudent() {
        String studentName = "Dhruva";
        when(mockedStudentRepository.findByName(studentName))
                .thenReturn(Optional.of(mockedStudentEntity));

        StudentDTO studentDTO = studentService.findStudentByName(mockedStudentEntity.getName());

        assertThat(studentDTO.getName()).isEqualTo(mockedStudentDTO.getName());
    }

    @Test
    void testFindStudentByName_whenStudentNotExist_thenThrowException() {
        when(mockedStudentRepository.findByName(anyString()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> studentService.findStudentByName(mockedStudentDTO.getName()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Student not found with the name: " + mockedStudentDTO.getName());
    }

    @Test
    void testGetAllStudents_whenStudentsAvailable_thenReturnStudents() {
        when(mockedStudentRepository.findAll())
                .thenReturn(List.of(mockedStudentEntity));

        List<StudentDTO> studentDTOList = studentService.getAllStudents();

        assertThat(studentDTOList).isNotNull();
        assertThat(studentDTOList.getFirst().getName()).isEqualTo(mockedStudentDTO.getName());
    }

    @Test
    void testGetAllStudents_whenStudentsNotAvailable_thenReturnEmptyList() {
        when(mockedStudentRepository.findAll())
                .thenReturn(List.of());

        List<StudentDTO> studentDTOList = studentService.getAllStudents();

        assertThat(studentDTOList).isEmpty();


    }

    @Test
    void testGetStudentById_whenStudentExist_thenReturnStudent() {
        when(mockedStudentRepository.findById(anyLong()))
                .thenReturn(Optional.of(mockedStudentEntity));
        when(mockedStudentRepository.existsById(anyLong())).thenReturn(true);

        StudentDTO studentDTO = studentService.getStudentById(1L);

        assertThat(studentDTO).isNotNull();
        assertThat(studentDTO.getId()).isEqualTo(1L);
        assertThat(studentDTO.getName()).isEqualTo(mockedStudentDTO.getName());
    }

    @Test
    void testGetStudentById_whenStudentNotExist_thenReturnThrowException() {
        when(mockedStudentRepository.existsById(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> studentService.getStudentById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Student is not available with the id: 1");

    }

    @Test
    void testSaveStudent_whenStudentIsValid_thenSaveAndReturnStudent() {
        when(mockedStudentRepository.save(mockedStudentEntity)).thenReturn(mockedStudentEntity);

        StudentDTO studentDTO = studentService.saveStudent(mockedStudentDTO);

        assertThat(studentDTO).isNotNull();
        assertThat(studentDTO.getId()).isEqualTo(mockedStudentEntity.getId());
        assertThat(studentDTO.getName()).isEqualTo(mockedStudentDTO.getName());
    }
}
