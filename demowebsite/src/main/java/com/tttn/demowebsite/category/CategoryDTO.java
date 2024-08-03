package com.tttn.demowebsite.category;


import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("parent_id")
    private Long parentId;
}
