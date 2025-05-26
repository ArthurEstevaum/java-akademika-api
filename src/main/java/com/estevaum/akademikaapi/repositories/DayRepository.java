package com.estevaum.akademikaapi.repositories;

import com.estevaum.akademikaapi.entities.Day;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<Day, Long> {
}
