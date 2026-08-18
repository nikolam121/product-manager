package hr.tis.academy.service.impl;

import hr.tis.academy.common.dto.*;
import hr.tis.academy.service.HelloService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class HelloServiceImpl implements HelloService {
    private String today() {
        return LocalDate.now().getDayOfWeek().name();
    }

    @Override
    public boolean isWeekend(String day) {
        return "SATURDAY".equals(day) || "SUNDAY".equals(day);
    }

    private List<String> oddDays() {
        List<String> oddDays = new ArrayList<>();
        Stream.of(DayOfWeek.values()).forEach(d -> {
            if (d.getValue() % 2 == 1) {
                oddDays.add(d.name());
            }
        });
        return oddDays;
    }

    private List<String> evenDays() {
        List<String> evenDays = new ArrayList<>();
        Stream.of(DayOfWeek.values()).forEach(d -> {
            if (d.getValue() % 2 == 0) {
                evenDays.add(d.name());
            }
        });
        return evenDays;
    }

    @Override
    public DayOfWeekResponse daysOfWeek() {
        String today = today();
        return new DayOfWeekResponse(today, isWeekend(today), oddDays(), evenDays());
    }
}
