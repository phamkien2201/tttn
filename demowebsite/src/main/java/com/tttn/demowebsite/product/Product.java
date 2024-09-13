package com.tttn.demowebsite.product;

import com.tttn.demowebsite.baseentity.BaseEntity;
import com.tttn.demowebsite.brand.Brand;
import com.tttn.demowebsite.category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 350)
    private String name;

    private Float price;
    private Float quantity;

    @Column(name = "description")
    private String description;

    @ElementCollection
    @CollectionTable(name = "product_thumbnails", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "thumbnail")
    private List<String> thumbnails;

    @ElementCollection
    @CollectionTable(name = "product_ingredients", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "ingredient")
    private List<String> ingredient;

    @Column(name = "user_manual")
    private String userManual;

    @ManyToOne
    @JoinColumn (name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn (name = "brand_id")
    private Brand brand;


}
