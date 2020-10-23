package com.beertech.products.model;

import com.beertech.products.controller.form.ProductForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "Código do produto")
    private Long id;
    @ApiModelProperty(value = "Nome do produto")
    private String name;
    @ApiModelProperty(value = "Descrição do produto")
    private String description;
    @ApiModelProperty(value = "Preço do produto")
    private BigDecimal price;

    public Product(ProductForm form) {
        this.name = form.getName();
        this.description = form.getDescription();
        this.price = form.getPrice();
    }
}
