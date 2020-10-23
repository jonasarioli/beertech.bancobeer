package com.beertech.products.controller;

import com.beertech.products.controller.dto.ProductDto;
import com.beertech.products.controller.form.ProductForm;
import com.beertech.products.model.Conta;
import com.beertech.products.model.Product;
import com.beertech.products.service.ProductService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/beercoins/product")
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
    public ResponseEntity<Page<Product>> allProducts(@PageableDefault(sort = "nome", direction = Sort.Direction.ASC, page = 0, size = 10) @ApiIgnore Pageable pageable) {
        Page<Product> products = productService.findAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping(value ="/{id}", produces = "application/json")
    public ResponseEntity<Product> prooductById(@PathVariable Long id) {
        Optional<Product> productById = productService.findProductById(id);
        if(productById.isPresent()) {
            return ResponseEntity.ok(productById.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductForm form, @ApiIgnore UriComponentsBuilder uriBuilder) {
        Product product = new Product(form);
        Product productSaved = productService.saveOrUpdateProduct(product);
        URI uri = uriBuilder.path("/beercoins/product/{id}").buildAndExpand(productSaved.getId()).toUri();
        return ResponseEntity.created(uri).body(productSaved);
    }

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
    public ResponseEntity rewardProoduct(@PathVariable Long producId, @ApiIgnore Principal principal) {
        Optional<Product> productById = productService.findProductById(producId);
        if(productById.isPresent()) {
            productService.rewardProduct(productById.get(), new Conta());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
