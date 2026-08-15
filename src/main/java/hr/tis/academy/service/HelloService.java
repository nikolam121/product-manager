package hr.tis.academy.service;

import hr.tis.academy.common.dto.*;
import org.springframework.stereotype.Component;

public interface HelloService {
    DayOfWeekResponse daysOfWeek();
    boolean isWeekend(String day);
}
