package hr.tis.academy.mappers;

import hr.tis.academy.common.dto.*;

import hr.tis.academy.model.ProductsOrder;
import hr.tis.academy.model.ProductsOrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "default")
public interface ProductsOrderMapper {

    ProductsOrderMapper INSTANCE = Mappers.getMapper(ProductsOrderMapper.class);

    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "store.storeName", target = "storeName")
    @Mapping(source = "deliveryEmployee.id", target = "deliveryEmployeeId")
    @Mapping(source = "deliveryEmployee.firstName", target = "deliveryEmployeeName")

    ProductsOrderDto toDto(ProductsOrder productsOrder);

    List<ProductsOrderDto> toDtoList(List<ProductsOrder> productsOrders);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")

         ProductsOrderItemDto toDto(ProductsOrderItem item);
}