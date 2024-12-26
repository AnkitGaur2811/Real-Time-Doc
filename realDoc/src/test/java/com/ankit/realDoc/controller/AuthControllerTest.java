package com.ankit.realDoc.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ankit.realDoc.entity.user;
import com.ankit.realDoc.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthController()).build();
    }

    @Test
    public void testRegister() throws Exception {
        user User = new user();
        User.setUserName("testUser");
        User.setEmail("test@example.com");
        User.setPassword("password");

        when(authService.register(User)).thenReturn(User);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"testUser\",\"email\":\"test@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("testUser"));
    }

    @Test
    public void testLogin() throws Exception {
        when(authService.login("test@example.com", "password")).thenReturn("token");

        mockMvc.perform(post("/auth/login")
                .param("email", "test@example.com")
                .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(content().string("Your session token: token"));
    }


    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(post("/auth/logout")
                .param("token", "token"))
                .andExpect(status().isOk())
                .andExpect(content().string("Logged out successfully"));
    }
}
