package com.tttn.demowebsite.brand;

import com.tttn.demowebsite.category.Category;
import com.tttn.demowebsite.category.CategoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/brands")
//@Validated
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @PostMapping("")
    @Operation(summary = "Tạo brand")
    public void createBrands(
            @Valid @RequestBody BrandDTO brandDTO,
            BindingResult result) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            ResponseEntity.badRequest().body(errorMessages);
        }
        brandService.createBrand(brandDTO);
    }


    @GetMapping("")//http://localhost:8080/api/v1/brands?page=1&limit=10
    @Operation(summary = "Lấy danh sách brand")
    public ResponseEntity<List<Brand>> getAllBrands(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit
    ) {
        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 100;
        }
        List<Brand> brands = brandService.getAllBrands(page, limit);
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy brand bằng id")
    public ResponseEntity<Brand> getCategoryById(@PathVariable("id") Long id) {
        Brand brand = brandService.getBrandById(id);
        return ResponseEntity.ok(brand);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật brand")
    public void updateBrands(
            @PathVariable Long id,
            @Valid @RequestBody BrandDTO brandDTO
    ) {
        brandService.updateBrand(id, brandDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa brand")
    public void deleteCategories(@PathVariable Long id) {
        brandService.deleteBrand(id);
    }
}
