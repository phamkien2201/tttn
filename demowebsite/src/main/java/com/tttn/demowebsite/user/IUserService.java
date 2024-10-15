package com.tttn.demowebsite.user;

import com.tttn.demowebsite.responses.UserDetailResponse;
import com.tttn.demowebsite.responses.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User createUser(UserDTO userDTO);
    String login(String phoneNumber, String password);
    Optional<User> findUserByPhoneNumber(String phoneNumber);
    User getUserDetailsById(Long userId);
    UserResponse updateUserById(Long userId, UserResponse userResponse);
    Page<UserDetailResponse> getAllUsers(int page, int limit);
    void setUserActiveStatus(Long userId, boolean isActive);
}
