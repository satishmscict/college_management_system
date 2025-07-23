package com.assignment.college_management_system.services;

import com.assignment.college_management_system.dtos.SubjectDTO;

public interface SubjectService {

    SubjectDTO assignSubjectToProfessor(Long subjectId, Long professorId);

    SubjectDTO getSubjectById(Long subjectId);

    SubjectDTO saveSubject(SubjectDTO subjectDTO);
}
