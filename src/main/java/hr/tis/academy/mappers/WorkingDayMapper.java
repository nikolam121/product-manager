package hr.tis.academy.mappers;

import hr.tis.academy.common.dto.*;
import hr.tis.academy.model.WorkingDay;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkingDayMapper {
    WorkingDayDto toDto(WorkingDay workingDay);
    @Mapping(ignore = true, target = "id")
    WorkingDay toEntity(WorkingDayDto workingDayDto);
}
