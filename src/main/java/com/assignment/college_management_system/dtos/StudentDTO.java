package com.assignment.college_management_system.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDTO {

    private Long id;

    @NotEmpty(message = "Student name is required.")
    @Size(min = 3, max = 30, message = "Student name is required minimum 3 and maximum 30 characters.")
    private String name;

    private Set<SubjectDTO> subjects;
}
