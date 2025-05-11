package com.assignment.college_management_system.dtos;

import com.assignment.college_management_system.entities.ProfessorEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {

    private Long id;

    @NotBlank(message = "Subject name is required.")
    private String name;

    private ProfessorEntity professor;
}
