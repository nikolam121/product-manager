package hr.tis.academy.mappers;

import hr.tis.academy.common.dto.*;
import hr.tis.academy.model.Position;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PositionMapper {

    PositionDto toDto(Position position);

    @Mapping(ignore = true, target = "employees")
    Position toEntity(PositionDto positionDto);
}