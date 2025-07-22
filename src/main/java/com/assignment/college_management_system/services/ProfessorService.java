package com.assignment.college_management_system.services;

import com.assignment.college_management_system.dtos.ProfessorDTO;

public interface ProfessorService {

    ProfessorDTO assignProfessorToStudent(Long professorId, Long studentId);

    ProfessorDTO getProfessorById(Long professorId);

    ProfessorDTO saveProfessor(ProfessorDTO professorDTO);
}
