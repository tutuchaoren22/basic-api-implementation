package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.api.RsController;
import com.thoughtworks.rslist.api.UserRegisterController;
import com.thoughtworks.rslist.entities.RsEvent;
import com.thoughtworks.rslist.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RsListApplicationTests {

    private MockMvc mockMvc;

    @BeforeEach
    void initMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(new RsController()).build();
    }

    @Test
    void shouldGetOneRsEvent() throws Exception {
        mockMvc.perform(get("/rs/list/1"))
                .andExpect(jsonPath("$.eventName", is("第一条事件")))
                .andExpect(jsonPath("$.keyword", is("分类一")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/2"))
                .andExpect(jsonPath("$.eventName", is("第二条事件")))
                .andExpect(jsonPath("$.keyword", is("分类二")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/3"))
                .andExpect(jsonPath("$.eventName", is("第三条事件")))
                .andExpect(jsonPath("$.keyword", is("分类三")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetRsEventGivenStartAndEnd() throws Exception {
        mockMvc.perform(get("/rs/list/?start=1&end=2"))
                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyword", is("分类一")))
                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[1].keyword", is("分类二")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/?start=2&end=3"))
                .andExpect(jsonPath("$[0].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[0].keyword", is("分类二")))
                .andExpect(jsonPath("$[1].eventName", is("第三条事件")))
                .andExpect(jsonPath("$[1].keyword", is("分类三")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/?start=1&end=3"))
                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyword", is("分类一")))
                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[1].keyword", is("分类二")))
                .andExpect(jsonPath("$[2].eventName", is("第三条事件")))
                .andExpect(jsonPath("$[2].keyword", is("分类三")))
                .andExpect(status().isOk());
    }

    //    @Test
    void shouldAddRsEventGivenEventNametAndKeyword() throws Exception {
        String requestJson = "{\"eventName\":\"第四条事件\",\"keyword\":\"分类四\"}";
        mockMvc.perform(post("/rs/add")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyword", is("分类一")))
                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[1].keyword", is("分类二")))
                .andExpect(jsonPath("$[2].eventName", is("第三条事件")))
                .andExpect(jsonPath("$[2].keyword", is("分类三")))
                .andExpect(jsonPath("$[3].eventName", is("第四条事件")))
                .andExpect(jsonPath("$[3].keyword", is("分类四")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateRsEventGivenIndex() throws Exception {
        String requestJsonAll = "{\"eventName\":\"要修改的事件\",\"keyword\":\"要修改的分类\"}";
        mockMvc.perform(post("/rs/update/1")
                .content(requestJsonAll)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/1"))
                .andExpect(jsonPath("$.eventName", is("要修改的事件")))
                .andExpect(jsonPath("$.keyword", is("要修改的分类")))
                .andExpect(status().isOk());

        String requestJsonOnlyEventName = "{\"eventName\":\"又要修改的事件\"}";
        mockMvc.perform(post("/rs/update/2")
                .content(requestJsonOnlyEventName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/2"))
                .andExpect(jsonPath("$.eventName", is("又要修改的事件")))
                .andExpect(jsonPath("$.keyword", is("分类二")))
                .andExpect(status().isOk());

        String requestJsonOnlyKeyword = "{\"keyword\":\"又要修改的分类\"}";
        mockMvc.perform(post("/rs/update/3")
                .content(requestJsonOnlyKeyword)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/3"))
                .andExpect(jsonPath("$.eventName", is("第三条事件")))
                .andExpect(jsonPath("$.keyword", is("又要修改的分类")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteRsEventGivenIndex() throws Exception {
        mockMvc.perform(post("/rs/delete/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[0].keyword", is("分类二")))
                .andExpect(status().isOk());
    }

    @Test
    void eventNameShouldNotNull() throws Exception {
        User user = new User("xiaowang", 19, "female", "a@thoughtworks.com", "18888888888");
        RsEvent rsEvent = new RsEvent(null, "娱乐", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/add")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void keywordShouldNotNull() throws Exception {
        User user = new User("xiaowang", 19, "female", "a@thoughtworks.com", "18888888888");
        RsEvent rsEvent = new RsEvent("添加一条热搜", null, user);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/add")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void userShouldNotNull() throws Exception {
        RsEvent rsEvent = new RsEvent("添加一条热搜", "娱乐", null);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/add")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ShouldAddRsEventWhenUserHasNotExist() throws Exception {
        User user = new User("xiaowang", 19, "female", "a@thoughtworks.com", "18888888888");
        RsEvent rsEvent = new RsEvent("添加一条热搜", "娱乐", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/add")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(1, UserRegisterController.userList.size());
    }

    @Test
    void ShouldAddRsEventWhenUserHasExist() throws Exception {
        User user = new User("xiaowang", 19, "female", "a@thoughtworks.com", "18888888888");
        RsEvent rsEvent = new RsEvent("添加一条热搜", "娱乐", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/add")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        RsEvent rsEvent2 = new RsEvent("添加第二条热搜", "生活", user);
        String userJson2 = objectMapper.writeValueAsString(rsEvent2);
        mockMvc.perform(post("/rs/add")
                .content(userJson2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(1, UserRegisterController.userList.size());
    }

}
