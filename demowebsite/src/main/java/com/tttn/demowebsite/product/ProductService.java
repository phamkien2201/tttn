package com.tttn.demowebsite.product;

import com.tttn.demowebsite.brand.Brand;
import com.tttn.demowebsite.brand.BrandRepository;
import com.tttn.demowebsite.category.Category;
import com.tttn.demowebsite.category.CategoryRepository;
import com.tttn.demowebsite.responses.ProductResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Override
    public void createProduct(ProductDTO productDTO) {
        Category existingCategory = categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Cannot find category with id: "+productDTO.getCategoryId()));
        Brand existingBrand = brandRepository
                .findById(productDTO.getBrandId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "cannot find brand with id: " +productDTO.getBrandId()));

        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .quantity(productDTO.getQuantity())
                .thumbnails(productDTO.getThumbnails())
                .ingredient(productDTO.getIngredients())
                .description(productDTO.getDescription())
                .category(existingCategory)
                .brand(existingBrand)
                .userManual(productDTO.getUserManual())
                .build();
        productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Cannot found product with id "+id));
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        return productRepository
                .findAll(pageRequest)
                .map(ProductResponse::fromProduct);
    }

    @Override
    public void updateProduct(
            long id, ProductDTO productDTO)
    {
        Product existingProduct = getProductById(id);
        if(existingProduct != null) {
            Category existingCategory = categoryRepository
                    .findById(productDTO.getCategoryId())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Cannot find category with id: "+productDTO.getCategoryId()));
            Brand existingBrand = brandRepository
                    .findById(productDTO.getBrandId())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "cannot find brand with id: " +productDTO.getBrandId()));

            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setBrand(existingBrand);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setQuantity(productDTO.getQuantity());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnails(productDTO.getThumbnails());
            existingProduct.setIngredient(productDTO.getIngredients());
            existingProduct.setUserManual(productDTO.getUserManual());
            productRepository.save(existingProduct);
        }
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.findById(id).ifPresent(productRepository::delete);
    }

}
