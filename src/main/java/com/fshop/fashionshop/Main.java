package com.fshop.fashionshop;

import com.fshop.fashionshop.model.Product;
import com.fshop.fashionshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class Main {
    @Autowired
    private static ProductRepository productRepository;

    public static void main(String[] args) {

        Product product = productRepository.findById(1l).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "product with this {id} not found in database"));

        System.out.println(product);

    }
}
