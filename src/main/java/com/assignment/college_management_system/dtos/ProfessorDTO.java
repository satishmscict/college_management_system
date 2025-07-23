package com.assignment.college_management_system.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessorDTO {

    private Long id;

    @NotBlank(message = "Professor name is required.")
    private String name;

    private Set<StudentDTO> students;
}
