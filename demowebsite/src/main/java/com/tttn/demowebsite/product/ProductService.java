package com.tttn.demowebsite.product;

import com.tttn.demowebsite.brand.Brand;
import com.tttn.demowebsite.brand.BrandRepository;
import com.tttn.demowebsite.category.Category;
import com.tttn.demowebsite.category.CategoryRepository;
import com.tttn.demowebsite.responses.ProductListResponse;
import com.tttn.demowebsite.responses.ProductResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public ProductListResponse findProductsByCategoryId(Long categoryId, int page, int limit) {
        // Tìm danh mục dựa trên categoryId
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new IllegalArgumentException("No category found with id: " + categoryId);
        }

        Category category = categoryOptional.get();

        // Kiểm tra nếu danh mục có danh mục con
        List<Category> subCategories = categoryRepository.findByParentId(categoryId);
        Page<Product> products;

        Pageable pageable = PageRequest.of(page, limit);

        if (subCategories.isEmpty()) {
            // Nếu không có danh mục con, tức là danh mục con, tìm sản phẩm trong danh mục hiện tại
            products = productRepository.findByCategoryId(categoryId, pageable);
        } else {
            // Nếu là danh mục lớn, tìm sản phẩm trong tất cả các danh mục con
            List<Long> categoryIds = findAllSubCategoryIds(categoryId);
            products = productRepository.findByCategoryIdIn(categoryIds, pageable);
        }

        // Lấy danh sách sản phẩm từ products
        List<ProductResponse> productResponses = products.map(ProductResponse::fromProduct).getContent();
        int totalPages = products.getTotalPages();

        return ProductListResponse.builder()
                .products(productResponses) // danh sách sản phẩm
                .totalPages(totalPages) // tổng số trang
                .build();
    }


    private List<Long> findAllSubCategoryIds(Long parentId) {
        List<Long> categoryIds = new ArrayList<>();
        categoryIds.add(parentId); // Thêm chính category lớn vào danh sách
        List<Category> subCategories = categoryRepository.findByParentId(parentId);
        for (Category subCategory : subCategories) {
            categoryIds.add(subCategory.getId());
            categoryIds.addAll(findAllSubCategoryIds(subCategory.getId())); // Recursive
        }
        return categoryIds;
    }

    @Override
    public ProductListResponse findProductsByBrandId(Long brandId, int page, int limit) {
        // Kiểm tra nếu tồn tại brand với brandId
        brandRepository.findById(brandId).orElseThrow(() ->
                new IllegalArgumentException("Cannot find brand with id: " + brandId)
        );

        // Phân trang
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<Product> products = productRepository.findByBrandId(brandId, pageRequest);

        List<ProductResponse> productResponses = products.map(ProductResponse::fromProduct).getContent();
        int totalPages = products.getTotalPages();

        return ProductListResponse.builder()
                .products(productResponses)
                .totalPages(totalPages)
                .build();
    }


}
