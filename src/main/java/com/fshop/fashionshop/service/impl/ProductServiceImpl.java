package com.fshop.fashionshop.service.impl;

import com.fshop.fashionshop.model.Product;
import com.fshop.fashionshop.repository.ProductRepository;
import com.fshop.fashionshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;


    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getById(long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "product with id:" + id + "  not found in database")
                );
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
    @Transactional
    @Override
    public Product update(long id, Product product) {
        Product fromDb = productRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "product with id:" + id + "  not found in database")
                );
        fromDb.setName(product.getName());
        fromDb.setPrice(product.getPrice());
        fromDb.setCurrency(product.getCurrency());
        fromDb.setDescription(product.getDescription());
        fromDb.setStock(product.getStock());
        return fromDb;
    }

    @Override
    public void delete(long id) {
        productRepository.deleteById(id);
    }
}
