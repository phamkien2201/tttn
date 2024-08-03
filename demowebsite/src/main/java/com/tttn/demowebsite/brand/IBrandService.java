package com.tttn.demowebsite.brand;


import java.util.List;

public interface IBrandService {

    void createBrand(BrandDTO brandDTO);
    Brand getBrandById(long id);
    List<Brand> getAllBrands();
    void updateBrand(long brandId, BrandDTO brandDTO);

    void deleteBrand(long id);
}
