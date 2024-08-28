package com.uh.herts.CartServiceAPI.repository;

import com.uh.herts.CartServiceAPI.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
//    List<Product> findById(Category category);

}
