package com.beertech.product;

import com.beertech.product.model.Product;
import com.beertech.product.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

@SpringBootApplication
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ProductService productService) {

        return args -> {
            Page<Product> allProducts = productService.findAllProducts(Pageable.unpaged());
            if (allProducts.getSize() <= 0) {
                productService.saveOrUpdateProduct(new Product(null, "Cerveja Colorado Cauim 600ml", "Lager", new BigDecimal("11.19"), "169376-1200-auto.webp"));
                productService.saveOrUpdateProduct(new Product(null, "Kit Original 2 Garrafas 600ml + 2 copos 190ml", "Lager", new BigDecimal("19.90"), "173514-1200-auto.jpg"));
                productService.saveOrUpdateProduct(new Product(null, "Cerveja Wäls Lager 473ml", "Lager", new BigDecimal("5.18"), "173442-1200-auto.webp"));
                productService.saveOrUpdateProduct(new Product(null, "Cerveja Antarctica Original 600ml Caixa (12 Unidades)", "Lager", new BigDecimal("75.48"), "175560-1200-auto.webp"));
                productService.saveOrUpdateProduct(new Product(null, "Cerveja Patagonia Bohemian Pilsener 473ml", "Pilsen", new BigDecimal("6.39"), "177921-1200-auto.png"));
                productService.saveOrUpdateProduct(new Product(null, "Cerveja Blondine Hallo 500 ml", "Trigo", new BigDecimal("12.95"), "174733-1200-auto.webp"));
                productService.saveOrUpdateProduct(new Product(null, "Cerveja Lohn Bier Vintage Red Ale 600ml", "Ale", new BigDecimal("16.14"), "175693-1200-auto.webp"));
                productService.saveOrUpdateProduct(new Product(null, "Cerveja Dádiva Fortune Ink Grisette 473ml", "Lager", new BigDecimal("16.14"), "175344-1200-auto.webp"));
                productService.saveOrUpdateProduct(new Product(null, "Cerveja Patagonia Amber Lager 473ml", "Lager", new BigDecimal("6.39"), "175507-1200-auto.webp"));
            }

        };
    }

}
