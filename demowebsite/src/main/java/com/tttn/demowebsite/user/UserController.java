package com.tttn.demowebsite.user;

import com.tttn.demowebsite.responses.LoginResponse;
import com.tttn.demowebsite.responses.UserDetailResponse;
import com.tttn.demowebsite.responses.UserListResponse;
import com.tttn.demowebsite.responses.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
    ) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body("Password does not match");
            }
           User user = userService.createUser(userDTO);
            return ResponseEntity.ok(user);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            // Thực hiện đăng nhập và lấy token
            String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());

            // Lấy thông tin người dùng
            Optional<User> optionalUser = userService.findUserByPhoneNumber(userLoginDTO.getPhoneNumber());

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                // Tạo đối tượng LoginResponse với thông tin người dùng và token
                LoginResponse response = LoginResponse.builder()
                        .message("Đăng nhập thành công")
                        .token(token)
                        .user(user)
                        .build();

                return ResponseEntity.ok(response);
            } else {
                // Xử lý trường hợp người dùng không tồn tại
                LoginResponse response = LoginResponse.builder()
                        .message("Người dùng không tồn tại")
                        .token(null)
                        .user(null)
                        .build();

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            // Xử lý các lỗi khác
            LoginResponse response = LoginResponse.builder()
                    .message("Đăng nhập không thành công")
                    .token(null)
                    .user(null)
                    .build();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<User> getUserDetails(@PathVariable Long id) {
        try {
            User user = userService.getUserDetailsById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/details/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserResponse userResponse) {
        try {
            UserResponse updatedUser = userService.updateUserById(id, userResponse);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/active/{id}")
    public ResponseEntity<Void> setUserActiveStatus(@PathVariable Long id, @RequestParam boolean isActive) {
        userService.setUserActiveStatus(id, isActive);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<UserListResponse> getAllUsers(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit
    ) {
        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 20;
        }

        // Lấy danh sách người dùng từ service
        Page<UserDetailResponse> userPage = userService.getAllUsers(page, limit);

        int totalPages = userPage.getTotalPages(); // Lấy tổng số trang
        List<UserDetailResponse> users = userPage.getContent(); // Lấy danh sách người dùng

        // Trả về danh sách người dùng cùng tổng số trang
        return ResponseEntity.ok(UserListResponse.builder()
                .users(users)
                .totalPages(totalPages)
                .build());
    }


}
