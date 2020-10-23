package com.beertech.products.controller.dto;

import com.beertech.products.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ProductDto {

    private String name;
    private String description;
    private BigDecimal price;

    public ProductDto(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }

    public static List<ProductDto> converter(List<Product> products) {
        return products.stream().map(ProductDto::new).collect(Collectors.toList());
    }

}
