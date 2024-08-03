package com.tttn.demowebsite.product;

import com.tttn.demowebsite.exceptions.DataNotFoundException;
import com.tttn.demowebsite.exceptions.InvaildParamException;
import com.tttn.demowebsite.productimage.ProductImage;
import com.tttn.demowebsite.productimage.ProductImageDTO;
import com.tttn.demowebsite.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface IProductService {
    void createProduct(ProductDTO productDTO) ;

    Product getProductById(long id);

    Page<ProductResponse> getAllProducts(PageRequest pageRequest);

    void updateProduct(long id, ProductDTO productDTO) ;

    void deleteProduct(long id);

    boolean existsByName(String name);

    ProductImage createProductImage(Long productId,
                                    ProductImageDTO productImageDTO)
            throws DataNotFoundException, InvaildParamException;
}
