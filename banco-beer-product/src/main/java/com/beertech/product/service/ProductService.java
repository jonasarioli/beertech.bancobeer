package com.beertech.product.service;

import com.beertech.product.controller.dto.Conta;
import com.beertech.product.exception.ProductRewardException;
import com.beertech.product.model.Product;
import com.beertech.product.model.amqp.RewardMessage;
import com.beertech.product.repository.ProductRepository;
import com.beertech.product.service.amqp.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Optional;

@Service
public class ProductService {

    @Value("${server.validation}")
    private String bancoUrl;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MessageSender messageSender;

    public Page<Product> findAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products;
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveOrUpdateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public void rewardProduct(Long productById, String token) throws ProductRewardException {
        Conta conta = validation(token);
        Optional<Product> product = findProductById(productById);
        if(!product.isPresent())
            throw new ProductRewardException(HttpStatus.NOT_FOUND, "Produto não existe!");
        if(conta.getSaldo().compareTo(product.get().getPrice()) >= 0) {
            RewardMessage message = new RewardMessage();
            message.setHash(conta.getHash());
            message.setProductName(product.get().getName());
            message.setProductPrice(product.get().getPrice());
            messageSender.sendRewardMessage(message);
        } else
            throw new ProductRewardException(HttpStatus.BAD_REQUEST, "Saldo insuficiente para a compra!");
    }

    private Conta validation(String token) throws ProductRewardException {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        try {
            LinkedHashMap result = restTemplate.postForObject(bancoUrl + "validacao/token", entity, LinkedHashMap.class);
            return new Conta(result.get("hash").toString(), new BigDecimal(result.get("saldo").toString()));
        } catch (HttpStatusCodeException ex) {
            throw new ProductRewardException(ex.getStatusCode(), ex.getMessage());
        }
    }
}
