package com.vodinh.prime.mappers;

import com.vodinh.prime.entities.Product;
import com.vodinh.prime.model.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO productDTO);
}
