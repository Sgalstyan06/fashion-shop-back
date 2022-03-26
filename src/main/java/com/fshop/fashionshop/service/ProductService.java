package com.fshop.fashionshop.service;

import com.fshop.fashionshop.model.Product;

import java.util.List;

public interface ProductService {

    Product create(Product product);

    Product getById(long id);

    List<Product> getAll();

    Product update(long id, Product product);

    void delete(long id);
}
