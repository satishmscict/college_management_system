package com.assignment.college_management_system.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdmissionRecordDTO {

    private Long id;

    @PositiveOrZero(message = "Salary should be positive or zero.")
    @DecimalMin(value = "1000.00", message = "Employee minium salary should not be less than 1000.50")
    @DecimalMax(value = "100000.00", message = "Employee maximum salary should not exceed the 99999.99")
    private Double fees;

    private StudentDTO student;
}
