package com.tttn.demowebsite.orderdetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {

    @JsonProperty("order_id")
    @Min(value = 1, message = "OrderID must be > 0")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "ProductID must be > 0")
    private Long productId;

    @Min(value = 0, message = "Total money must be >= 0")
    private Float price;

    @JsonProperty("number_of_products")
    @Min(value = 1, message = "number of products must be >= 1")
    private int numberOfProducts;

    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be >= 0")
    private Float totalMoney;

}
