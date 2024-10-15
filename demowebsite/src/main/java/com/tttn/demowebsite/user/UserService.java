package com.tttn.demowebsite.user;

import com.tttn.demowebsite.components.JwtTokenUtil;
import com.tttn.demowebsite.responses.UserDetailResponse;
import com.tttn.demowebsite.responses.UserResponse;
import com.tttn.demowebsite.role.Role;
import com.tttn.demowebsite.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public User createUser(UserDTO userDTO) {
        //Register user
        String phoneNumber = userDTO.getPhoneNumber();
        //kiem tra sdt ton tai kh
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists! ");
        }

        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found!"));
//        if (role.getName().equals(Role.ADMIN)){
//            throw new DataIntegrityViolationException(" You cannot register an admin account ");
//        }
        //convert from userDTO => user
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();
        newUser.setRole(role);
        // neu co accountId thi khong yeu cau password
        if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) {

        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Invalid phone number / password");
        }
        User existingUser = optionalUser.get();

        if (existingUser.isActive()) { // Kiểm tra nếu active là true
            throw new IllegalArgumentException("User is active. Login failed.");
        }

        //check password
        if(existingUser.getFacebookAccountId() == 0
                && existingUser.getGoogleAccountId() == 0) {
         if(!passwordEncoder.matches(password, existingUser.getPassword())) {
             throw new BadCredentialsException("wrong phone number or password");
         }
        }
        //authenticate with java spring security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber, password,
                existingUser.getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public User getUserDetailsById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
    }

    @Override
    public UserResponse updateUserById(Long userId, UserResponse userResponse) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        // Cập nhật các trường cần thiết
        existingUser.setFullName(userResponse.getFullName());
        existingUser.setAddress(userResponse.getAddress());
        existingUser.setAddress(userResponse.getAddress());
        existingUser.setDateOfBirth(userResponse.getDateOfBirth());

        User updatedUser = userRepository.save(existingUser);

        // Trả về đối tượng UserResponse
        return userResponse;
    }

    @Override
    public Page<UserDetailResponse> getAllUsers(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<User> userPage = userRepository.findAll(pageRequest);
        return userPage.map(user -> {
            UserDetailResponse userDetailResponse = new UserDetailResponse();
            userDetailResponse.setFullName(user.getFullName());
            userDetailResponse.setId(user.getId());
            userDetailResponse.setAddress(user.getAddress());
            userDetailResponse.setPhoneNumber(user.getPhoneNumber());
            userDetailResponse.setDateOfBirth(user.getDateOfBirth());
            userDetailResponse.setActive(user.isActive());
            return userDetailResponse;
        });
    }


    @Override
    public void setUserActiveStatus(Long userId, boolean isActive) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
        existingUser.setActive(isActive);
        userRepository.save(existingUser);
    }
}
