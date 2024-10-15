package com.tttn.demowebsite.responses;

import lombok.*;

import java.util.Date;


@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String fullName;
    private String email;
    private String address;
    private Date dateOfBirth;

}
