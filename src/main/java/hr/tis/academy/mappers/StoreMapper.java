package hr.tis.academy.mappers;

import hr.tis.academy.common.dto.*;
import hr.tis.academy.model.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, WorkingDayMapper.class, PositionMapper.class, EmployeeMapper.class})
public interface StoreMapper {
    StoreDto toDto(Store store);

    Store toEntity(StoreDto storeDto);
    List<StoreDto> toDtoList(List<Store> stores);

}
