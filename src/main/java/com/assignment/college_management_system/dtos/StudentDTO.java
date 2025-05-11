package com.assignment.college_management_system.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    @NotEmpty(message = "Student name is required.")
    @Size(min = 3, max = 30, message = "Student name is required minimum 3 and maximum 30 characters.")
    private String name;
}
