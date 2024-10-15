package com.tttn.demowebsite.product;

import com.tttn.demowebsite.responses.ProductListResponse;
import com.tttn.demowebsite.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IProductService {
    void createProduct(ProductDTO productDTO) ;

    Product getProductById(long id);

    Page<ProductResponse> getAllProducts(PageRequest pageRequest);

    void updateProduct(long id, ProductDTO productDTO) ;

    void deleteProduct(long id);
    ProductListResponse findProductsByCategoryId(Long categoryId, int page, int limit);
    ProductListResponse findProductsByBrandId(Long brandId, int page, int limit);
}
