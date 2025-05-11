package com.assignment.college_management_system.dtos;

import com.assignment.college_management_system.entities.SubjectEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDTO {

    private Long id;

    @NotBlank(message = "Professor name is required.")
    private String name;

    private List<SubjectEntity> subjectList;
}
