package com.tttn.demowebsite.product;

import com.tttn.demowebsite.category.Category;
import com.tttn.demowebsite.orderdetail.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);//phan trang
    List<Product> findByCategoryId(Long categoryId);

    // Tìm sản phẩm theo danh sách categoryId
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
    Page<Product> findByCategoryIdIn(List<Long> categoryIds, Pageable pageable);

    Page<Product> findByBrandId(Long brandId, Pageable pageable);
}
