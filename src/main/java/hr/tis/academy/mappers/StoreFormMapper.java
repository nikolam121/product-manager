package hr.tis.academy.mappers;

import hr.tis.academy.common.dto.StoreDto;
import hr.tis.academy.common.dto.StoreForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoreFormMapper {
    @Mapping(ignore = true, target = "id")
    @Mapping(source = "city", target = "address.city")
    @Mapping(source = "country", target = "address.country")
    @Mapping(source = "streetName", target = "address.streetName")
    @Mapping(source = "houseNumber", target = "address.houseNumber")
    @Mapping(ignore = true, target = "workingDays")
    @Mapping(ignore = true, target = "employees")
    StoreDto toDto(StoreForm storeForm);

    @Mapping(source = "address.city", target = "city")
    @Mapping(source = "address.country", target = "country")
    @Mapping(source = "address.streetName", target = "streetName")
    @Mapping(source = "address.houseNumber", target = "houseNumber")
    StoreForm toEntity(StoreDto storeDto);
}
