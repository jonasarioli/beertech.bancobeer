package com.beertech.product.controller;

import static org.mockito.BDDMockito.given;

import com.beertech.product.controller.form.ProductForm;
import com.beertech.product.model.Product;
import com.beertech.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void allProducts() throws Exception {

        given(productService.findAllProducts(Mockito.any(PageRequest.class))).willReturn(new PageImpl<Product>(new ArrayList<Product>()));
        MvcResult andReturn = this.mockMvc.perform(get("/beercoins/product"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void prooductById() throws Exception {
        Long id = 1l;
        given(productService.findProductById(id)).willReturn(Optional.of(new Product(id, "produto", "produto de teste", new BigDecimal(100), "")));
        MvcResult andReturn = this.mockMvc.perform(get("/beercoins/product/1"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void createProduct() throws Exception {
        Product product = new Product(null, "produto", "produto de teste", new BigDecimal(100), "");
        Product productSaved = new Product(null, "produto", "produto de teste", new BigDecimal(100), "");
        given(productService.saveOrUpdateProduct(product)).willReturn(productSaved);
        MvcResult andReturn = this.mockMvc.perform(post("/beercoins/product")
                .content(asJsonString(new ProductForm("produto", "produto de teste", new BigDecimal(100))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
    }

    @Test
    void updateProduct() throws Exception {
        Product product = new Product(1l, "produto", "produto de teste", new BigDecimal(100), "");
        Product producUpdated = new Product(null, "produto alterado", "produto de teste alterado", new BigDecimal(100), "");
        given(productService.findProductById(1l)).willReturn(Optional.of(new Product(1l, "produto", "produto de teste", new BigDecimal(100), "")));
        given(productService.saveOrUpdateProduct(product)).willReturn(producUpdated);
        MvcResult andReturn = this.mockMvc.perform(put("/beercoins/product/1")
                .content(asJsonString(new ProductForm("produto", "produto de teste", new BigDecimal(100))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
    }

    @Test
    void deleteProduct() throws Exception {
        given(productService.findProductById(1l)).willReturn(Optional.of(new Product(1l, "produto", "produto de teste", new BigDecimal(100), "")));
        MvcResult andReturn = this.mockMvc.perform(delete("/beercoins/product/1")).andExpect(status().isAccepted()).andReturn();
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}