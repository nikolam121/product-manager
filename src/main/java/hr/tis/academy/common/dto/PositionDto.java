package hr.tis.academy.common.dto;

import hr.tis.academy.common.enums.Level;
import hr.tis.academy.common.enums.Title;

public record PositionDto(Long id, Title title, Level level) {
}
