package hr.tis.academy.common.dto;

import java.time.LocalTime;

public record WorkingDayDto(String dayOfWeek, LocalTime startTime, LocalTime endTime) {
}
