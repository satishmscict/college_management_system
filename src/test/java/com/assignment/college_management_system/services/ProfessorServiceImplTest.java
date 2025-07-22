package com.assignment.college_management_system.services;

import com.assignment.college_management_system.JpaTestContainerConfiguration;
import com.assignment.college_management_system.dtos.ProfessorDTO;
import com.assignment.college_management_system.entities.ProfessorEntity;
import com.assignment.college_management_system.entities.StudentEntity;
import com.assignment.college_management_system.exceptions.ResourceNotFoundException;
import com.assignment.college_management_system.repositories.ProfessorRepository;
import com.assignment.college_management_system.repositories.StudentRepository;
import com.assignment.college_management_system.utils.ValidationUtils;
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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Import(JpaTestContainerConfiguration.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProfessorServiceImplTest {

    @Spy
    private ModelMapper mockedModelMapper;

    @Mock
    private ProfessorRepository mockedProfessorRepository;

    @Mock
    private StudentRepository mockedStudentRepository;

    @Spy
    private ValidationUtils mockedValidationUtils;

    @InjectMocks
    private ProfessorServiceImpl professorService;

    private ProfessorEntity mockedProfessorEntity;
    private ProfessorDTO mockedProfessorDTO;

    @BeforeEach
    void setup() {
        mockedProfessorEntity = ProfessorEntity.builder()
                .id(1L)
                .name("Arpit")
                .build();
        mockedProfessorDTO = mockedModelMapper.map(mockedProfessorEntity, ProfessorDTO.class);
    }

    @Test
    void testAssignProfessorToStudent_whenProfessorNotExistAndSubjectExist_thenThrowException() {
        when(mockedProfessorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> professorService.assignProfessorToStudent(1L,1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Professor not found with the id: 1");

        verify(mockedProfessorRepository, atLeastOnce()).findById(1L);
        verify(mockedProfessorRepository, never()).save(any());
        verify(mockedStudentRepository,never()).findById(1L);
    }

    @Test
    void testAssignProfessorToStudent_whenProfessorExistAndSubjectNotExist_thenThrowException() {
        ProfessorEntity professorEntity = mock(ProfessorEntity.class);

        when(mockedProfessorRepository.findById(1L)).thenReturn(Optional.of(professorEntity));
        when(mockedStudentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> professorService.assignProfessorToStudent(1L,1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Student not found with the id: 1");

        verify(mockedProfessorRepository, atLeastOnce()).findById(1L);
        verify(mockedProfessorRepository, never()).save(any());
        verify(mockedStudentRepository,atLeastOnce()).findById(1L);
    }

    @Test
    void testAssignProfessorToStudent_whenProfessorAndSubjectIsValid_thenSaveProfessorWithStudent() {
        ProfessorEntity professorEntity = mock(ProfessorEntity.class);
        StudentEntity studentEntity = mock(StudentEntity.class);

        when(professorEntity.getName()).thenReturn("Arpit");

        Set<StudentEntity> studentEntitySet = new HashSet<>();
        when(professorEntity.getStudents()).thenReturn(studentEntitySet);

        when(mockedProfessorRepository.findById(1L)).thenReturn(Optional.of(professorEntity));
        when(mockedProfessorRepository.save(any())).thenReturn(professorEntity);
        when(mockedStudentRepository.findById(1L)).thenReturn(Optional.of(studentEntity));

        ProfessorDTO professorDTO =  professorService.assignProfessorToStudent(1L,1L);

        assertThat(professorDTO).isNotNull();
        assertThat(professorDTO.getName()).isEqualTo("Arpit");

        verify(mockedProfessorRepository, atLeastOnce()).findById(1L);
        verify(mockedProfessorRepository, atLeastOnce()).save(any());
        verify(mockedStudentRepository,atLeastOnce()).findById(1L);
    }

    @Test
    void testGetProfessorById_whenProfessorExist_thenReturnProfessor() {
        when(mockedProfessorRepository.findById(any())).thenReturn(Optional.of(mockedProfessorEntity));

       ProfessorDTO professorDTO =  professorService.getProfessorById(1L);

        assertThat(professorDTO.getId()).isEqualTo(1);
        assertThat(professorDTO.getName()).isEqualTo("Arpit");
        verify(mockedProfessorRepository, atLeastOnce()).findById(1L);
    }

    @Test
    void testGetProfessorById_whenProfessorNotExist_thenException() {
        when(mockedProfessorRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> professorService.getProfessorById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Professor not found with the id: 1");

        verify(mockedProfessorRepository, atLeastOnce()).findById(1L);
    }

    @Test
    void testSaveProfessor_whenProfessorIsValid_thenSaveProfessor() {
        when(mockedProfessorRepository.save(mockedProfessorEntity)).thenReturn(mockedProfessorEntity);

        ProfessorDTO professorDTO = professorService.saveProfessor(mockedProfessorDTO);

        assertThat(professorDTO.getId()).isEqualTo(1L);
        assertThat(professorDTO.getName()).isEqualTo("Arpit");
        verify(mockedProfessorRepository, atLeastOnce()).save(any());
    }
}
