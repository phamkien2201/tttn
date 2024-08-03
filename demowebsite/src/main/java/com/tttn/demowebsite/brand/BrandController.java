package com.tttn.demowebsite.brand;

import com.tttn.demowebsite.category.Category;
import com.tttn.demowebsite.category.CategoryDTO;
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
    public ResponseEntity<List<Brand>> getAllBrands(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        List<Brand> brands = brandService.getAllBrands();
        return ResponseEntity.ok(brands);
    }


    @PutMapping("/{id}")
    public void updateBrands(
            @PathVariable Long id,
            @Valid @RequestBody BrandDTO brandDTO
    ) {
        brandService.updateBrand(id, brandDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCategories(@PathVariable Long id) {
        brandService.deleteBrand(id);
    }
}
