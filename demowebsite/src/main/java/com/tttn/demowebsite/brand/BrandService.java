package com.tttn.demowebsite.brand;

import com.tttn.demowebsite.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class
BrandService implements IBrandService {

    private final BrandRepository brandRepository;
    @Override
    public void createBrand(BrandDTO brandDTO) {
        Brand newBrand= Brand
                .builder()
                .name(brandDTO.getName())
                .thumbnail(brandDTO.getThumbnail())
                .build();
        brandRepository.save(newBrand);
    }

    @Override
    public Brand getBrandById(long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
    }

    @Override
    public List<Brand> getAllBrands(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<Brand> brandPage = brandRepository.findAll(pageRequest);
        return brandPage.getContent();
    }

    @Override
    public void updateBrand(long brandId, BrandDTO brandDTO) {
        Brand existingBrand = getBrandById(brandId);
        existingBrand.setName(brandDTO.getName());
        existingBrand.setThumbnail(brandDTO.getThumbnail());
        brandRepository.save(existingBrand);
    }

    @Override
    public void deleteBrand(long id) {
        brandRepository.deleteById(id);
    }
}
