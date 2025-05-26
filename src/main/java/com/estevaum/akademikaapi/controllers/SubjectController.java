package com.estevaum.akademikaapi.controllers;

import com.estevaum.akademikaapi.DTOs.subject.DeadlineDTO;
import com.estevaum.akademikaapi.DTOs.subject.SubjectCreationDTO;
import com.estevaum.akademikaapi.DTOs.subject.SubjectResponseDTO;
import com.estevaum.akademikaapi.DTOs.subject.SubjectUpdateDTO;
import com.estevaum.akademikaapi.entities.Day;
import com.estevaum.akademikaapi.entities.Subject;
import com.estevaum.akademikaapi.services.SubjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    private final Function<Subject, SubjectResponseDTO>  subjectToResponseDTO = subject -> {
         return new SubjectResponseDTO(subject.getId(),
                subject.getName(), subject.getQuarter(), subject.getStatus(),
                subject.getSyllabus(), subject.getTeacher(),
                subject.getDays().stream().map(Day::getDay).toList(),
                subject.getDeadlines().stream().map(deadline -> new DeadlineDTO(deadline.getDate(), deadline.getName())).toList()
        );
    };

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/")
    public ResponseEntity<List<SubjectResponseDTO>> getAllUserSubjects() {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Subject> userSubjects = subjectService.getAllUserSubjects(authenticatedUserEmail);
        List <SubjectResponseDTO> userSubjectsResponse = userSubjects.stream().map(subjectToResponseDTO).toList();

        return ResponseEntity.ok(userSubjectsResponse);
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<SubjectResponseDTO> getUserSubject(@PathVariable Long subjectId) {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Subject userSubject = subjectService.getUserSubject(subjectId, authenticatedUserEmail);
        SubjectResponseDTO subjectResponse = subjectToResponseDTO.apply(userSubject);

        return ResponseEntity.ok(subjectResponse);
    }

    @PostMapping("/")
    public ResponseEntity<SubjectResponseDTO> createSubject(@Valid @RequestBody SubjectCreationDTO requestData) {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Subject subject = subjectService.createSubject(requestData, authenticatedUserEmail);
        SubjectResponseDTO subjectResponse = subjectToResponseDTO.apply(subject);

        return ResponseEntity.status(201).body(subjectResponse);
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<SubjectResponseDTO> updateSubject(@Valid @RequestBody SubjectUpdateDTO requestData, @PathVariable Long subjectId) {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Subject subject = subjectService.updateUserSubject(subjectId, authenticatedUserEmail, requestData);
        SubjectResponseDTO subjectResponse = subjectToResponseDTO.apply(subject);

        return ResponseEntity.ok(subjectResponse);
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long subjectId) {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        subjectService.deleteSubject(subjectId, authenticatedUserEmail);

        return ResponseEntity.ok("Disciplina excluída com sucesso.");
    }
}
