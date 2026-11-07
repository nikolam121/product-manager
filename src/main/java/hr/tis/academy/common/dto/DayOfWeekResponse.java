package hr.tis.academy.common.dto;

import java.util.List;

public record DayOfWeekResponse(String today, boolean isWeekend, List<String> oddDays, List<String> evenDays) {
}
