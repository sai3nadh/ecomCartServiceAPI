package com.uh.herts.CartServiceAPI.service;


import com.uh.herts.CartServiceAPI.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductById(Integer id);
    Product createProduct(Product product);
    Product updateProduct(Integer id, Product productDetails);
    void deleteProduct(Integer id);
}
