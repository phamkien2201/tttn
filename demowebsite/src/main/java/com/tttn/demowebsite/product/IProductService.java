package com.tttn.demowebsite.product;

import com.tttn.demowebsite.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface IProductService {
    void createProduct(ProductDTO productDTO) ;

    Product getProductById(long id);

    Page<ProductResponse> getAllProducts(PageRequest pageRequest);

    void updateProduct(long id, ProductDTO productDTO) ;

    void deleteProduct(long id);


}
