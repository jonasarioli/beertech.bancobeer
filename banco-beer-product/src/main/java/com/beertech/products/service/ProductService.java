package com.beertech.products.service;

import com.beertech.products.model.Conta;
import com.beertech.products.model.Product;
import com.beertech.products.model.amqp.RewardMessage;
import com.beertech.products.repository.ProductRepository;
import com.beertech.products.service.amqp.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MessageSender messageSender;

    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
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

    public void rewardProduct(Product product, Conta conta) {
        if(conta.getSaldo().compareTo(product.getPrice()) >= 0) {
            RewardMessage message = new RewardMessage();
            message.setHash(conta.getHash());
            message.setProductName(product.getName());
            message.setProductPrice(product.getPrice());
            messageSender.sendRewardMessage(message);
        }
    }

}
