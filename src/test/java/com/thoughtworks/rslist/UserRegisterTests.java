package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.api.UserRegisterController;
import com.thoughtworks.rslist.entities.User;
import com.thoughtworks.rslist.entities.UserEntity;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRegisterTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        UserRegisterController.userList.clear();
    }

    @AfterEach
    void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    void shouldRegisterUser() throws Exception {
        User user = new User("xiaowang", 19, "female", "a@thoughtworks.com", "18888888888");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/login")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("index", String.valueOf(UserRegisterController.userList.size() - 1)))
                .andExpect(status().isCreated());
    }

    @Test
    void userNameShouldNotNull() throws Exception {
        User user = new User(null, 19, "female", "a@thoughtworks.com", "18888888888");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/login")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid user")));
    }

    @Test
    void userNameSizeShouldLessThanEight() throws Exception {
        User user = new User("xiaowangaa", 19, "female", "a@thoughtworks.com", "18888888888");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/login")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid user")));
    }

    @Test
    void genderShouldNotNull() throws Exception {
        User user = new User("xiaowang", 19, null, "a@thoughtworks.com", "18888888888");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/login")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid user")));
    }

    @Test
    void ageShouldMoreThanEighteen() throws Exception {
        User user = new User("xiaowang", 10, "female", "a@thoughtworks.com", "18888888888");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/login")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid user")));
    }

    @Test
    void ageShouldLessThanOneHundred() throws Exception {
        User user = new User("xiaowang", 101, "female", "a@thoughtworks.com", "18888888888");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/login")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid user")));
    }

    @Test
    void ageShouldNotNull() throws Exception {
        User user = new User("xiaowang", null, "female", "a@thoughtworks.com", "18888888888");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/login")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid user")));
    }

    @Test
    void emailShouldFormat() throws Exception {
        User user = new User("xiaowang", 19, "female", "athoughtworks.com", "18888888888");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/login")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid user")));
    }

    @Test
    void phoneShouldNotNull() throws Exception {
        User user = new User("xiaowang", 19, "female", "a@thoughtworks.com", null);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/login")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid user")));
    }

    @Test
    void phoneShouldFormat() throws Exception {
        User user = new User("xiaowang", 19, "female", "a@thoughtworks.com", "88888888");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/login")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid user")));
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        User user = new User("xiaowang", 19, "female", "a@thoughtworks.com", "18888888888");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/login")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("index", String.valueOf(UserRegisterController.userList.size() - 1)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/users"))
                .andExpect(jsonPath("$.[0]", hasKey("user_name")))
                .andExpect(jsonPath("$.[0]", hasKey("user_age")))
                .andExpect(jsonPath("$.[0]", hasKey("user_gender")))
                .andExpect(jsonPath("$.[0]", hasKey("user_email")))
                .andExpect(jsonPath("$.[0]", hasKey("user_phone")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddUserToDb() throws Exception {
        User user = new User("xiaowang", 19, "female", "a@thoughtworks.com", "18888888888");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/login")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        List<UserEntity> users = userRepository.findAll();
        assertEquals(1, users.size());
        assertEquals("xiaowang", users.get(0).getUserName());
    }

    @Test
    void shouldGetUserById() throws Exception {
        User user = new User("xiaowang", 19, "female", "a@thoughtworks.com", "18888888888");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/login")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/user/1"))
                .andExpect(jsonPath("$.userName", is("xiaowang")))
                .andExpect(jsonPath("$.age", is(19)))
                .andExpect(jsonPath("$.gender", is("female")))
                .andExpect(jsonPath("$.email", is("a@thoughtworks.com")))
                .andExpect(jsonPath("$.phone", is("18888888888")))
                .andExpect(status().isOk());
    }


}
