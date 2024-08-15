package com.tttn.demowebsite.user;

public interface IUserService {
    User createUser(UserDTO userDTO);
    String login(String phoneNumber, String password);
}
