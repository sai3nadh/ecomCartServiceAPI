package com.uh.herts.CartServiceAPI.service.impl;

import com.uh.herts.CartServiceAPI.entity.Product;
import com.uh.herts.CartServiceAPI.exception.ResourceNotFoundException;
import com.uh.herts.CartServiceAPI.repository.ProductRepository;
import com.uh.herts.CartServiceAPI.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Integer id, Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + id));
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + id));
        productRepository.delete(product);
    }
}
