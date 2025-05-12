package com.assignment.college_management_system.controllers;

import com.assignment.college_management_system.dtos.ProfessorDTO;
import com.assignment.college_management_system.services.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professor/v1")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping
    public ResponseEntity<ProfessorDTO> saveProfessor(@RequestBody @Valid ProfessorDTO professorDTO) {
        return ResponseEntity.ok(professorService.saveProfessor(professorDTO));
    }

    @GetMapping(path = "/{professorId}")
    public ResponseEntity<ProfessorDTO> getProfessorById(@PathVariable Long professorId) {
        return ResponseEntity.ok(professorService.getProfessorById(professorId));
    }

    @PutMapping(path = "/{professorId}/student/{studentId}")
    private ResponseEntity<ProfessorDTO> assignProfessorToStudent(
            @PathVariable Long professorId,
            @PathVariable Long studentId
    ) throws Exception {
        return ResponseEntity.ok(professorService.assignProfessorToStudent(professorId, studentId));
    }
}
