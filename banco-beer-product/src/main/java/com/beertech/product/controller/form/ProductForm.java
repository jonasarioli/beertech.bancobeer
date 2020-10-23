package com.beertech.product.controller.form;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductForm {
    private String name;
    private String description;
    private BigDecimal price;
}
