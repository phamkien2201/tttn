package com.tttn.demowebsite.brand;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandDTO {

    @NotEmpty(message = "Brand name do not empty")
    private String name;
}
