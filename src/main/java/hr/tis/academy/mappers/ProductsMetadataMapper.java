package hr.tis.academy.mappers;


import hr.tis.academy.common.dto.*;
import hr.tis.academy.model.ProductsMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductsMetadataMapper {

    ProductsMetadataMapper INSTANCE = Mappers.getMapper(ProductsMetadataMapper.class);

    ProductsMetadataDto toDto(ProductsMetadata productsMetadata);
}