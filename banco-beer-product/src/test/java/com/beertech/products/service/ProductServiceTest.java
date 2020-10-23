package com.beertech.products.service;

import com.beertech.products.model.Product;
import com.beertech.products.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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