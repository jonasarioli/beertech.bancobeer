package com.beertech.product.service;

import com.beertech.product.model.Conta;
import com.beertech.product.model.Product;
import com.beertech.product.model.amqp.RewardMessage;
import com.beertech.product.repository.ProductRepository;
import com.beertech.product.service.amqp.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductService {
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

    public void rewardProduct(Product product, String hash, BigDecimal saldo) {
        if(saldo.compareTo(product.getPrice()) >= 0) {
            RewardMessage message = new RewardMessage();
            message.setHash(hash);
            message.setProductName(product.getName());
            message.setProductPrice(product.getPrice());
            messageSender.sendRewardMessage(message);
        }
    }

}
