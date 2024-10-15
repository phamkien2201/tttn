package com.tttn.demowebsite.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserListResponse {
    private List<UserDetailResponse> users;
    private int totalPages;
}
