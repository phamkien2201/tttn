package com.tttn.demowebsite.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailResponse {

    private Long id;
    private String fullName;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String email;
    private String address;
    private Date dateOfBirth;
    private boolean active;
}
