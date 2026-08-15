package hr.tis.academy.mappers;

import hr.tis.academy.common.dto.*;
import hr.tis.academy.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto toDto(Address address);

    @Mapping(ignore = true, target = "id")
    Address toEntity(AddressDto addressDto);
}

