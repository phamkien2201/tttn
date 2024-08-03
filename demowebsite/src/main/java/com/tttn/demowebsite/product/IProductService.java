package com.tttn.demowebsite.product;

import com.tttn.demowebsite.exceptions.DataNotFoundException;
import com.tttn.demowebsite.exceptions.InvaildParamException;
import com.tttn.demowebsite.productimage.ProductImage;
import com.tttn.demowebsite.productimage.ProductImageDTO;
import com.tttn.demowebsite.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws DataNotFoundException;

    Product getProductById(long id) throws DataNotFoundException;

    Page<ProductResponse> getAllProducts(PageRequest pageRequest);

    Product updateProduct(long id, ProductDTO productDTO) throws DataNotFoundException;

    void deleteProduct(long id);

    boolean existsByName(String name);

    ProductImage createProductImage(Long productId,
                                    ProductImageDTO productImageDTO)
            throws DataNotFoundException, InvaildParamException;
}
