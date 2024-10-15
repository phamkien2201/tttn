package com.tttn.demowebsite.cart;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tttn.demowebsite.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY,  orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> cartItems;

}
