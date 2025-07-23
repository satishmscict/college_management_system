package com.assignment.college_management_system.services;

import com.assignment.college_management_system.JpaTestContainerConfiguration;
import com.assignment.college_management_system.dtos.SubjectDTO;
import com.assignment.college_management_system.entities.ProfessorEntity;
import com.assignment.college_management_system.entities.SubjectEntity;
import com.assignment.college_management_system.exceptions.ResourceNotFoundException;
import com.assignment.college_management_system.repositories.ProfessorRepository;
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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Import(JpaTestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest {

    @Spy
    private ModelMapper mockedModelMapper;

    @Mock
    private ProfessorRepository mockedProfessorRepository;

    @Mock
    private SubjectRepository mockedSubjectRepository;

    @Spy
    private ValidationUtils validationUtils;

    @InjectMocks
    private SubjectServiceImpl subjectService;

    private SubjectEntity mockedSubjectEntity;
    private SubjectDTO mockedSubjecTDto;

    @BeforeEach
    void setup() {
        mockedSubjectEntity = SubjectEntity.builder()
                .id(1L)
                .name("DBMS")
                .build();

        mockedSubjecTDto = mockedModelMapper.map(mockedSubjectEntity, SubjectDTO.class);
    }

    @Test
    void testAssignSubjectToProfessor_whenSubjectAndProfessorIsValid_thenSaveAndReturnSubjectWithProfessor() {
        ProfessorEntity professor = mock(ProfessorEntity.class);
        SubjectEntity subject = mock(SubjectEntity.class);

        when(subject.getName()).thenReturn("Database");

        when(mockedProfessorRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(mockedSubjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(mockedSubjectRepository.save(subject)).thenReturn(subject);

        SubjectDTO subjectDTO = subjectService.assignSubjectToProfessor(1L, 1L);

        assertThat(subjectDTO.getName()).isEqualTo("Database");

        verify(mockedSubjectRepository, atLeast(1)).findById(1L);
        verify(mockedSubjectRepository, atLeastOnce()).save(any());
        verify(mockedProfessorRepository, atLeastOnce()).findById(1L);
    }

    @Test
    void testGetSubjectById_whenSubjectIdExist_thenReturnSubject() {
        when(mockedSubjectRepository.findById(any())).thenReturn(Optional.of(mockedSubjectEntity));
        when(mockedSubjectRepository.existsById(1L)).thenReturn(true);

        SubjectDTO subjectDTO = subjectService.getSubjectById(1L);

        assertThat(subjectDTO.getId()).isEqualTo(mockedSubjectEntity.getId());
        assertThat(subjectDTO.getName()).isEqualTo(mockedSubjectEntity.getName());

        verify(mockedSubjectRepository, atLeastOnce()).findById(any());
        verify(mockedSubjectRepository, atLeastOnce()).findById(1L);
    }

    @Test
    void testGetSubjectById_whenSubjectNotExist_thenThrowException() {
        when(mockedSubjectRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThatThrownBy(() -> subjectService.getSubjectById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Subject not available with the id: 1");


        verify(mockedSubjectRepository, never()).findById(any());
        verify(mockedSubjectRepository, only()).existsById(any());
    }

    @Test
    void testSaveSubject_whenSubjectIsValid_thenSaveSubject() {
        when(mockedSubjectRepository.save(mockedSubjectEntity)).thenReturn(mockedSubjectEntity);

        SubjectDTO subjectDTO = subjectService.saveSubject(mockedSubjecTDto);

        assertThat(subjectDTO.getName()).isEqualTo(mockedSubjectEntity.getName());
        assertThat(subjectDTO.getId()).isEqualTo(mockedSubjectEntity.getId());

        verify(mockedSubjectRepository, atLeastOnce()).save(any());
    }
}
