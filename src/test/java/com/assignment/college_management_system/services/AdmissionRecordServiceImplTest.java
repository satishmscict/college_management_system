package com.assignment.college_management_system.services;

import com.assignment.college_management_system.JpaTestContainerConfiguration;
import com.assignment.college_management_system.dtos.AdmissionRecordDTO;
import com.assignment.college_management_system.entities.AdmissionRecordEntity;
import com.assignment.college_management_system.entities.StudentEntity;
import com.assignment.college_management_system.exceptions.ResourceNotFoundException;
import com.assignment.college_management_system.repositories.AdmissionRecordRepository;
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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Import(JpaTestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class AdmissionRecordServiceImplTest {

    @Mock
    private AdmissionRecordRepository mockedAdmissionRecordRepository;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private StudentRepository mockedStudentRepository;

    @Spy
    private ValidationUtils ValidationUtils;

    @InjectMocks
    private AdmissionRecordServiceImpl admissionRecordService;

    private AdmissionRecordEntity mockedAdmissionRecordEntity;
    private AdmissionRecordDTO mockedAdmissionRecordDTO;

    @BeforeEach
    void setup() {
        mockedAdmissionRecordEntity = AdmissionRecordEntity.builder()
                .id(1L)
                .fees(30000.00)
                .build();

        mockedAdmissionRecordDTO = modelMapper.map(mockedAdmissionRecordEntity, AdmissionRecordDTO.class);
    }

    @Test
    void testAssignAdmissionRecordToStudent_whenAdmissionNotExistAndStudentExist_thenThrowException() {
       StudentEntity studentEntity = mock(StudentEntity.class);
        when(mockedStudentRepository.findById(any())).thenReturn(Optional.of(studentEntity));
        when(mockedAdmissionRecordRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> admissionRecordService.assignAdmissionRecordToStudent(1L,1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Admission record not available with the id: 1");

        verify(mockedStudentRepository, atLeastOnce()).findById(1L);
        verify(mockedAdmissionRecordRepository, atLeastOnce()).findById(1L);
        verify(mockedAdmissionRecordRepository, never()).save(any());
    }


    @Test
    void testAssignAdmissionRecordToStudent_whenAdmissionExistAndStudentNotExist_thenThrowException() {
        when(mockedStudentRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> admissionRecordService.assignAdmissionRecordToStudent(1L,1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Student not available with the id: 1");

        verify(mockedStudentRepository, atLeastOnce()).findById(1L);
        verify(mockedAdmissionRecordRepository, never()).findById(1L);
        verify(mockedAdmissionRecordRepository, never()).save(any());
    }

    @Test
    void testAssignAdmissionRecordToStudent_whenAdmissionAndStudentExist_thenReturnAdmissionWithStudent() {
        AdmissionRecordEntity admissionRecordEntity = mock(AdmissionRecordEntity.class);
        StudentEntity studentEntity = mock(StudentEntity.class);

        when(admissionRecordEntity.getId()).thenReturn(1L);
        when(admissionRecordEntity.getFees()).thenReturn(25000.00);

        when(mockedAdmissionRecordRepository.findById(any())).thenReturn(Optional.of(admissionRecordEntity));
        when(mockedStudentRepository.findById(any())).thenReturn(Optional.of(studentEntity));
        when(mockedAdmissionRecordRepository.save(any())).thenReturn(admissionRecordEntity);

        AdmissionRecordDTO admissionRecordDTO =  admissionRecordService.assignAdmissionRecordToStudent(1L, 1L);

        assertThat(admissionRecordDTO.getId()).isEqualTo(1L);
        assertThat(admissionRecordDTO.getFees()).isEqualTo(25000.00);

        verify(mockedStudentRepository, atLeastOnce()).findById(1L);
        verify(mockedAdmissionRecordRepository, atLeastOnce()).findById(1L);
        verify(mockedAdmissionRecordRepository, atLeastOnce()).save(any());
    }

    @Test
    void testGetAdmissionRecordById_whenAdmissionRecordExist_thenReturnAdmissionRecord() {
        when(mockedAdmissionRecordRepository.existsById(1L)).thenReturn(true);
        when(mockedAdmissionRecordRepository.findById(1L)).thenReturn(Optional.of(mockedAdmissionRecordEntity));

        AdmissionRecordDTO admissionRecordDTO = admissionRecordService.getAdmissionRecordById(1L);

        assertThat(admissionRecordDTO.getId()).isEqualTo(mockedAdmissionRecordDTO.getId());
        assertThat(admissionRecordDTO.getFees()).isEqualTo(mockedAdmissionRecordDTO.getFees());

        verify(mockedAdmissionRecordRepository, atLeastOnce()).existsById(1L);
        verify(mockedAdmissionRecordRepository, atLeastOnce()).findById(any());
    }

    @Test
    void testGetAdmissionRecordById_whenAdmissionRecordNotExist_thenThrowException() {
        when(mockedAdmissionRecordRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> admissionRecordService.getAdmissionRecordById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Admission record not available with the id: 1");

        verify(mockedAdmissionRecordRepository, atLeastOnce()).existsById(1L);
        verify(mockedAdmissionRecordRepository, never()).findById(any());
    }

    @Test
    void testSaveAdmissionRecord_whenAdmissionRecordIsValid_thenReturnAdmissionRecord() {
        when(mockedAdmissionRecordRepository.save(any())).thenReturn(mockedAdmissionRecordEntity);

        AdmissionRecordDTO admissionRecordDTO = admissionRecordService.saveAdmissionRecord(mockedAdmissionRecordDTO);

        assertThat(admissionRecordDTO.getId()).isEqualTo(1L);
        assertThat(admissionRecordDTO.getFees()).isEqualTo(30000.00);

        verify(mockedAdmissionRecordRepository, atLeastOnce()).save(any());
    }
}
