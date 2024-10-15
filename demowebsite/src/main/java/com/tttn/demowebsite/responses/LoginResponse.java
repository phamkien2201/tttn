package com.tttn.demowebsite.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tttn.demowebsite.user.User;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("token")
    private String token;

    private User user;
}
