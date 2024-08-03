package com.tttn.demowebsite.category;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    @NotEmpty(message = "Category name do not empty")
    private String name;
}
