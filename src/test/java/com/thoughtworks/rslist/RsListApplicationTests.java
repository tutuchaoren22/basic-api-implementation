package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.api.RsController;
import com.thoughtworks.rslist.api.UserRegisterController;
import com.thoughtworks.rslist.entities.RsEvent;
import com.thoughtworks.rslist.entities.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RsListApplicationTests {

    @Autowired
    private MockMvc mockMvc;

//    @BeforeEach
//    void initMockMvc() {
//        mockMvc = MockMvcBuilders.standaloneSetup(new RsController()).build();
//    }

    @Test
    @Order(1)
    void shouldGetOneRsEvent() throws Exception {
        mockMvc.perform(get("/rs/list/1"))
                .andExpect(jsonPath("$.eventName", is("第一条事件")))
                .andExpect(jsonPath("$.keyword", is("分类一")))
                .andExpect(jsonPath("$", not(hasKey("user"))))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/2"))
                .andExpect(jsonPath("$.eventName", is("第二条事件")))
                .andExpect(jsonPath("$.keyword", is("分类二")))
                .andExpect(jsonPath("$", not(hasKey("user"))))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/3"))
                .andExpect(jsonPath("$.eventName", is("第三条事件")))
                .andExpect(jsonPath("$.keyword", is("分类三")))
                .andExpect(jsonPath("$", not(hasKey("user"))))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void shouldGetRsEventGivenStartAndEnd() throws Exception {
        mockMvc.perform(get("/rs/list/?start=1&end=2"))
                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyword", is("分类一")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[1].keyword", is("分类二")))
                .andExpect(jsonPath("$[1]", not(hasKey("user"))))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/?start=2&end=3"))
                .andExpect(jsonPath("$[0].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[0].keyword", is("分类二")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(jsonPath("$[1].eventName", is("第三条事件")))
                .andExpect(jsonPath("$[1].keyword", is("分类三")))
                .andExpect(jsonPath("$[1]", not(hasKey("user"))))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/?start=1&end=3"))
                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyword", is("分类一")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[1].keyword", is("分类二")))
                .andExpect(jsonPath("$[1]", not(hasKey("user"))))
                .andExpect(jsonPath("$[2].eventName", is("第三条事件")))
                .andExpect(jsonPath("$[2].keyword", is("分类三")))
                .andExpect(jsonPath("$[2]", not(hasKey("user"))))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void shouldUpdateRsEventGivenIndex() throws Exception {
        String eventJson = "{\"eventName\":\"要修改的事件\"," +
                " \"keyword\":\"要修改的分类\"," +
                "\"user\" :{\"user_name\":\"xiaowang\", \"user_gender\":\"male\", \"user_age\":22, \"user_email\":\"d@b.com\", \"user_phone\":\"12345678904\"}}";

        mockMvc.perform(post("/rs/update/1")
                .content(eventJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("index", "0"))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/rs/list/1"))
                .andExpect(jsonPath("$.eventName", is("要修改的事件")))
                .andExpect(jsonPath("$.keyword", is("要修改的分类")))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void shouldDeleteRsEventGivenIndex() throws Exception {
        mockMvc.perform(post("/rs/delete/1"))
                .andExpect(header().string("index", "0"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[0].keyword", is("分类二")))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
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
    @Order(5)
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
    @Order(6)
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
    @Order(7)
    void ShouldAddRsEventWhenUserHasNotExist() throws Exception {
        String eventJson = "{\"eventName\":\"添加一条热搜\"," +
                " \"keyword\":\"娱乐\"," +
                "\"user\" :{\"user_name\":\"xiaowang\", \"user_gender\":\"female\", \"user_age\":19, \"user_email\":\"a@thoughtworks.com\", \"user_phone\":\"18888888888\"}}";

        mockMvc.perform(post("/rs/add")
                .content(eventJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("index", String.valueOf(RsController.rsList.size() - 1)))
                .andExpect(status().isCreated());

        assertEquals(1, UserRegisterController.userList.size());
    }

    @Test
    @Order(8)
    void ShouldAddRsEventWhenUserHasExist() throws Exception {
        String eventJson = "{\"eventName\":\"添加一条热搜\"," +
                " \"keyword\":\"娱乐\"," +
                "\"user\" :{\"user_name\":\"xiaowang\", \"user_gender\":\"female\", \"user_age\":19, \"user_email\":\"a@thoughtworks.com\", \"user_phone\":\"18888888888\"}}";

        mockMvc.perform(post("/rs/add")
                .content(eventJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("index", String.valueOf(RsController.rsList.size() - 1)))
                .andExpect(status().isCreated());

        String eventJson2 = "{\"eventName\":\"添加第二条热搜\"," +
                " \"keyword\":\"生活\"," +
                "\"user\" :{\"user_name\":\"xiaowang\", \"user_gender\":\"female\", \"user_age\":19, \"user_email\":\"a@thoughtworks.com\", \"user_phone\":\"18888888888\"}}";

        mockMvc.perform(post("/rs/add")
                .content(eventJson2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("index", String.valueOf(RsController.rsList.size() - 1)))
                .andExpect(status().isCreated());

        assertEquals(1, UserRegisterController.userList.size());
    }

    @Test
    @Order(9)
    void ShouldReturnExceptionWhenGetEventWithInvalidIndex() throws Exception {
        mockMvc.perform(get("/rs/list/10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid index")));
    }

    @Test
    @Order(10)
    void ShouldReturnExceptionWhenGetEventWithInvalidParam() throws Exception {
        mockMvc.perform(get("/rs/list/?start=-1&end=10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid request param")));
    }

    @Test
    @Order(11)
    void shouldReturnExceptionwhenEventIsInvalid() throws Exception {
        String rsEventJson = "{\"id\":4,\"eventName\":null,\"keyWord\":\"娱乐\"," +
                "\"user\":{\"user_name\":\"wang\",\"user_gender\":\"male\",\"user_age\":19," +
                "\"user_email\":\"A@thoughtworks.com\",\"user_phone\":\"11234567890\"}}";

        mockMvc.perform(post("/rs/add").content(rsEventJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid param")));
    }

}
