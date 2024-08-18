package com.tttn.demowebsite.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tttn.demowebsite.product.Product;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends BaseResponse {
    private Long id;
    private String name;
    private Float price;
    private List<String> thumbnails;
    private List<String> ingredients;
    private String description;
    private String userManual;

    @JsonProperty("category_id")
    private Long categoryId;
    private String categoryName;
    @JsonProperty("brand_id")
    private Long brandId;
    private String brandName;
    public static ProductResponse fromProduct(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .thumbnails(product.getThumbnail())
                .ingredients(product.getIngredient())
                .description(product.getDescription())
                .userManual(product.getUserManual())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .brandId(product.getBrand().getId())
                .brandName(product.getBrand().getName())
                .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }
}
