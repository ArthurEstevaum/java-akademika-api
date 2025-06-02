package com.estevaum.akademikaapi.services;

import com.estevaum.akademikaapi.DTOs.subject.SubjectCreationDTO;
import com.estevaum.akademikaapi.DTOs.subject.SubjectUpdateDTO;
import com.estevaum.akademikaapi.entities.Day;
import com.estevaum.akademikaapi.entities.Deadline;
import com.estevaum.akademikaapi.entities.Subject;
import com.estevaum.akademikaapi.entities.User;
import com.estevaum.akademikaapi.repositories.DayRepository;
import com.estevaum.akademikaapi.repositories.DeadlineRepository;
import com.estevaum.akademikaapi.repositories.SubjectRepository;
import com.estevaum.akademikaapi.repositories.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final DayRepository dayRepository;
    private final UserRepository userRepository;
    private final DeadlineRepository deadlineRepository;

    SubjectService(SubjectRepository subjectRepository, UserRepository userRepository, DayRepository dayRepository, DeadlineRepository deadlineRepository) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.dayRepository = dayRepository;
        this.deadlineRepository = deadlineRepository;
    }

    public Subject createSubject(SubjectCreationDTO requestData, String userEmail) {

        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NoSuchElementException("Nenhum usuário encontrado com este id"));
        Subject subject = new Subject(user, requestData.name(), requestData.status(), requestData.quarter(), requestData.teacher(), requestData.syllabus());

        List<Day> dayList = dayRepository.findAll();

        Set<Day> subjectDays = dayList.stream().filter(day -> requestData.days().contains(day.getDay())).collect(Collectors.toSet());
        subject.setDays(subjectDays);

        List<Deadline> deadlines = requestData.deadlines().stream().map(deadlineDTO -> new Deadline(deadlineDTO.name(), deadlineDTO.date(), subject)).toList();

        subjectRepository.save(subject);
        deadlineRepository.saveAll(deadlines);

        return subject;
    }

    public List<Subject> getAllUserSubjects(String userEmail) {
        return subjectRepository.findAllByUserEmail(userEmail);
    }

    public Subject getUserSubject(Long id, String userEmail) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Nenhuma disciplina encontrada com este ID"));
        String subjectUserEmail = subject.getUser().getEmail();

        if(!Objects.equals(userEmail, subjectUserEmail)) {
            throw new BadCredentialsException("Acesso negado a esta disciplina: Ela não pertence à você");
        }

        return subject;
    }

    public Subject updateUserSubject(Long id, String userEmail, SubjectUpdateDTO requestData) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Nenhuma disciplina encontrada com este ID"));
        String subjectUserEmail = subject.getUser().getEmail();

        if(!Objects.equals(userEmail, subjectUserEmail)) {
            throw new BadCredentialsException("Acesso negado a esta disciplina: Ela não pertence à você");
        }

        List<Day> dayList = dayRepository.findAll();

        Set<Day> subjectDays = dayList.stream().filter(day -> requestData.days().contains(day.getDay())).collect(Collectors.toSet());

        List<Deadline> deadlines = requestData.deadlines().stream().map(deadlineDTO -> new Deadline(deadlineDTO.name(), deadlineDTO.date(), subject)).toList();

        subject.setDays(subjectDays);
        subject.setName(requestData.name());
        subject.setTeacher(requestData.teacher());
        subject.setStatus(requestData.status());
        deadlineRepository.saveAll(deadlines);

        return subjectRepository.save(subject);
    }

    public boolean deleteSubject(Long id, String userEmail) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Nenhuma disciplina encontrada com este ID"));
        String subjectUserEmail = subject.getUser().getEmail();

        if(!Objects.equals(userEmail, subjectUserEmail)) {
            throw new BadCredentialsException("Acesso negado a esta disciplina: Ela não pertence à você");
        }

        subjectRepository.delete(subject);
        return true;
    }
}
