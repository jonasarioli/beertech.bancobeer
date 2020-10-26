package com.beertech.product.controller;

import com.beertech.product.controller.dto.ContaDto;
import com.beertech.product.controller.form.ProductForm;
import com.beertech.product.controller.form.RewardForm;
import com.beertech.product.model.Conta;
import com.beertech.product.model.Product;
import com.beertech.product.service.ProductService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URI;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Optional;

@RestController
@RequestMapping("/beercoins/product")
@CrossOrigin
public class ProductController {

    @Autowired
    ProductService productService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de produtos"),
            @ApiResponse(code = 401, message = "Você não esta autorizado para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Pagina a ser carregada", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Quantidade de registros", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Ordenacao dos registros")
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<Product>> allProducts(@ApiIgnore UriComponentsBuilder uriBuilder, @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 10) @ApiIgnore Pageable pageable) {
        Page<Product> products = productService.findAllProducts(pageable);

        for(Product product : products ) {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/beercoins/product/image/")
                    .path(product.getImageName())
                    .toUriString();
            product.setImageName(fileDownloadUri);
        }
        return ResponseEntity.ok(products);
    }
    @ApiIgnore
    @GetMapping(value ="/{id}", produces = "application/json")
    public ResponseEntity<Product> prooductById(@PathVariable Long id) {
        Optional<Product> productById = productService.findProductById(id);
        if(productById.isPresent()) {
            return ResponseEntity.ok(productById.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @ApiIgnore
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductForm form, @ApiIgnore UriComponentsBuilder uriBuilder) {
        Product product = new Product(form);
        Product productSaved = productService.saveOrUpdateProduct(product);
        URI uri = uriBuilder.path("/beercoins/product/{id}").buildAndExpand(productSaved.getId()).toUri();
        return ResponseEntity.created(uri).body(productSaved);
    }
    @ApiIgnore
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductForm form) {
        Optional<Product> productById = productService.findProductById(id);
        if(productById.isPresent()) {
            Product product = productById.get();
            product.setDescription(form.getDescription());
            product.setName(form.getName());
            product.setPrice(form.getPrice());
            productService.saveOrUpdateProduct(product);
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @ApiIgnore
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Optional<Product> productById = productService.findProductById(id);
        if(productById.isPresent()) {
            productService.deleteProduct(productById.get());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/reward/{id}")
    public ResponseEntity rewardProoduct(@RequestBody RewardForm form) {
        Optional<Product> productById = productService.findProductById(form.getProdutoId());
        if(productById.isPresent()) {
            String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiUGVyZmlsIjoiUk9MRV9VU0VSIiwiU2FsZG8iOjEwMC4wMCwiTm9tZSI6IlVTRVIiLCJIYXNoIjoiNWM0N2QyNWUzZTk4YjUzYWJjNTMyYTNlNzcyMzAzN2YiLCJleHAiOjE2MDM3NTI4MDksImlhdCI6MTYwMzY2NjQwOX0.1QjFFj5lxdD2DMO8Dfhkkir0Uari6Ire_WkozmBPuMM";
            ContaDto conta = null;
            try {
                conta = validation(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            productService.rewardProduct(productById.get(), conta.getHash(), conta.getSaldo());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiIgnore
    @RequestMapping(value = "/image/{name}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getImage(@PathVariable String name) throws IOException {

        ClassPathResource classPathResource = new ClassPathResource("META-INF/resources/" + name);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(classPathResource.getInputStream()));
    }

    public ContaDto validation(String token) throws Exception {
        LinkedHashMap result = null;
        if (token != null) {
            if (token.contains("Basic")) {
                return null;
            }
            HttpHeaders headers = new HttpHeaders();
            RestTemplate restTemplate = new RestTemplate();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<String>(null, headers);
            try {
                result = restTemplate.postForObject("http://localhost:8080/validacao/token", entity, LinkedHashMap.class);
            } catch (RestClientException e) {
                e.printStackTrace();
                return null;
            }
            ContaDto response = new ContaDto(result.get("hash").toString(), new BigDecimal(result.get("saldo").toString()), result.get("nome").toString(), result.get("email").toString(), result.get("cnpj").toString());


            return response;

        }
        return null;
}}
