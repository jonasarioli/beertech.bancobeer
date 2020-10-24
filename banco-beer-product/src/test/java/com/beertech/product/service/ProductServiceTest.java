package com.beertech.product.service;

import com.beertech.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void findAllProducts() {

    }

    @Test
    void findProductById() {
    }

    @Test
    void saveOrUpdateProduct() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void rewardProduct() {
    }
}