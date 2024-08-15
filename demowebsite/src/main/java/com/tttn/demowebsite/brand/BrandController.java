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
@CrossOrigin(origins = "http://localhost:5173")
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
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        List<Brand> brands = brandService.getAllBrands();
        return ResponseEntity.ok(brands);
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
