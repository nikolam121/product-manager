package hr.tis.academy.service;

import hr.tis.academy.common.dto.*;

public interface HelloService {
    DayOfWeekResponse daysOfWeek();
    boolean isWeekend(String day);
}
