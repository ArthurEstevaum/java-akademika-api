package com.estevaum.akademikaapi.repositories;

import com.estevaum.akademikaapi.entities.Deadline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeadlineRepository extends JpaRepository<Deadline, Long> {
}
