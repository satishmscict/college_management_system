package com.assignment.college_management_system.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "student")
@NoArgsConstructor
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(mappedBy = "student")
    @JsonIgnore
    private AdmissionRecordEntity admissionRecord;

    @ManyToMany(mappedBy = "students")
    @JsonIgnore
    private Set<ProfessorEntity> professors;
}
