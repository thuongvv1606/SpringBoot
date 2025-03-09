package com.example.identity_service.service;

import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.response.UserResponse;
import com.example.identity_service.entity.User;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCreationRequest userCreationRequest;
    private UserResponse userResponse;
    private User user;
    private LocalDate dob;

    @BeforeEach
    void initData(){
        dob = LocalDate.of(2002, 01, 01);
        userCreationRequest =  UserCreationRequest.builder()
                .username("admin")
                .firstName("admin")
                .lastName("admin")
                .password("admin1234")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("775cda1e-0361-4dd7-a44c-4675bb3ec6bc")
                .username("admin")
                .firstName("admin")
                .lastName("admin")
                .dob(dob)
                .build();

        user = User.builder()
                .id("775cda1e-0361-4dd7-a44c-4675bb3ec6bc")
                .username("admin")
                .firstName("admin")
                .lastName("admin")
                .dob(dob).build();
    }

    @Test
    void createUser_validRequest_success(){
        //WHEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        //WHEN,
        var userResponse = userService.createUser(userCreationRequest);

        //THEN
        Assertions.assertThat(userResponse.getId()).isEqualTo("775cda1e-0361-4dd7-a44c-4675bb3ec6bc");
        Assertions.assertThat(userResponse.getUsername()).isEqualTo("admin");
    }

    @Test
    void createUser_userExist_failure(){
        //WHEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        //WHEN,
        var exception = assertThrows(AppException.class,()->userService.createUser(userCreationRequest));

        //THEN
        Assertions.assertThat(exception.getMessage()).isEqualTo("User already existed");
    }

    @Test
    @WithMockUser(username = "admin")
    void getMyInfo_validRequest_success(){
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        var response = userService.getMyInfo();

        Assertions.assertThat(response.getUsername()).isEqualTo("admin");
    }

    @Test
    @WithMockUser(username = "admin")
    void getMyInfo_userNotFound_failure(){
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));

        //WHEN,
        var exception = assertThrows(AppException.class,()->userService.getMyInfo());

        //THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(206);
    }
}
