package com.tttn.demowebsite.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Toi thieu la 3 ky tu và tối đa là 200")
    private String name;

    @Min(value = 0, message = "gia khong the nho hon 0")
    @Max(value = 100000000, message = "gia khong the lon hon 10,000,000")
    private Float price;

    private List<String> thumbnails; // Updated to match List<String>
    private List<String> ingredients;
    private String description;
    private String userManual;

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("brand_id")
    private Long brandId;
}
