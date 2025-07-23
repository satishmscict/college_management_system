package com.assignment.college_management_system.controllers;

import com.assignment.college_management_system.dtos.SubjectDTO;
import com.assignment.college_management_system.services.SubjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<SubjectDTO> saveSubject(@RequestBody @Valid SubjectDTO subjectDTO) {
        return new ResponseEntity<>(subjectService.saveSubject(subjectDTO), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{subjectId}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable Long subjectId) {
        return ResponseEntity.ok(subjectService.getSubjectById(subjectId));
    }

    @PutMapping("/{subjectId}/professor/{professorId}")
    public ResponseEntity<SubjectDTO> assignSubjectToProfessor(
            @PathVariable Long subjectId,
            @PathVariable Long professorId
    ) {
       return ResponseEntity.ok(subjectService.assignSubjectToProfessor(subjectId,professorId));
    }
}
