package com.assignment.college_management_system.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    private Long id;

    @NotEmpty(message = "Student name is required.")
    @Size(min = 3, max = 30, message = "Student name is required minimum 3 and maximum 30 characters.")
    private String name;

    private Set<SubjectDTO> subjects;
}
