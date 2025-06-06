package com.estevaum.akademikaapi.repositories;

import com.estevaum.akademikaapi.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findAllByUserEmail(String userEmail);
}
