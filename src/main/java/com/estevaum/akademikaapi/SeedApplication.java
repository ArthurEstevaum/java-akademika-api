package com.estevaum.akademikaapi;

import com.estevaum.akademikaapi.entities.Day;
import com.estevaum.akademikaapi.enums.Days;
import com.estevaum.akademikaapi.repositories.DayRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class SeedApplication implements CommandLineRunner {

    private final DayRepository dayRepository;

    public SeedApplication(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(dayRepository.count() == 0) {
            List<Days> daysEnum = List.of(Days.MONDAY, Days.TUESDAY, Days.THURSDAY, Days.WEDNESDAY, Days.FRIDAY, Days.SATURDAY);

            List<Day> dayEntities = daysEnum.stream().map(Day::new).toList();

            dayRepository.saveAll(dayEntities);
        }
    }
}
