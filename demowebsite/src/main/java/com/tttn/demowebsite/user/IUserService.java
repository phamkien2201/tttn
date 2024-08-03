package com.tttn.demowebsite.user;

import com.tttn.demowebsite.exceptions.DataNotFoundException;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;
    String login(String phoneNumber, String password);
}
