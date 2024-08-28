package com.uh.herts.CartServiceAPI.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false)
    private Timestamp updatedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;

    // Transient field for the categoryId
    @Transient
    @JsonProperty("categoryId")
    public Integer getCategoryId() {
        return category != null ? category.getId() : null;
    }

    // Optionally, if you want to set the category via the categoryId
    public void setCategoryId(Integer categoryId) {
        if (categoryId != null) {
            this.category = new Category();
//            this.category.setCategoryId(categoryId);
            this.category.setId(categoryId);
        } else {
            this.category = null;
        }
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images;
}
