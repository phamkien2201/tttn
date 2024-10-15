package com.tttn.demowebsite.product;


import com.tttn.demowebsite.responses.ProductListResponse;
import com.tttn.demowebsite.responses.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;


import java.util.List;


@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping("")
    @Operation(summary = "Lấy danh sách sản phẩm")
    public ResponseEntity<ProductListResponse> getProducts(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit",required = false) Integer limit
    ) {
        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 20;
        }
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("createdAt").descending()
        );
        Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);
        int totalPages = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse.builder()
                .products(products)
                .totalPages(totalPages)
                .build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy sản phẩm bằng id")
    public ResponseEntity<Product> getProductById(
            @PathVariable("id") Long productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @PostMapping("")
    @Operation(summary = "Tạo sản phẩm mới")
    public void createProduct(
            @Valid @RequestBody ProductDTO productDTO) {
        productService.createProduct(productDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật sản phẩm")
    public void updateProductById(
            @PathVariable("id") Long id,
            @RequestBody ProductDTO productDTO) {
        productService.updateProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa sản phẩm")
    public void deleteProductById(
            @PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Lấy danh sách sản phẩm theo danh mục")
    public ResponseEntity<ProductListResponse> findProductsByCategoryId(
            @PathVariable Long categoryId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit",required = false) Integer limit
    ) {
        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 20;
        }

        ProductListResponse productListResponse = productService.findProductsByCategoryId(categoryId, page, limit);
        return ResponseEntity.ok(productListResponse);
    }

    @GetMapping("/brand/{brandId}")
    @Operation(summary = "Lấy danh sách sản phẩm theo thương hiệu")
    public ResponseEntity<ProductListResponse> findProductsByBrandId(
            @PathVariable Long brandId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit
    ) {
        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 20;
        }

        ProductListResponse productListResponse = productService.findProductsByBrandId(brandId, page, limit);
        return ResponseEntity.ok(productListResponse);
    }

}
