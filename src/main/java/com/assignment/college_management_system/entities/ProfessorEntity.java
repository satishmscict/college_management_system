package com.assignment.college_management_system.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "professor")
public class ProfessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProfessorEntity professor = (ProfessorEntity) o;
        return Objects.equals(id, professor.id) && Objects.equals(name, professor.name) && Objects.equals(age, professor.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "professor_student_mapping",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<StudentEntity> students;

    @OneToMany(mappedBy = "professor")
    @JsonIgnore
    private List<SubjectEntity> subjectList;
}
