package com.tttn.demowebsite.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "social_accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider", length = 300, nullable = false)
    private String provider;

    @Column(name = "provider_id", length = 30, nullable = false)
    private String providerId;

    @Column(name = "name", length = 300, nullable = false)
    private String name;

    @Column(name = "email", length = 300)
    private String email;

}
