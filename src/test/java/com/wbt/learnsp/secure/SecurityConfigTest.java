package com.wbt.learnsp.secure;

import com.wbt.learnsp.controller.GreetingController;
import com.wbt.learnsp.service.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = {GreetingController.class})
class SecurityConfigTest {

    @Autowired
    MockMvc mMvc;

    @MockBean
    VideoService mVideoService;

    @Test
    void unauthenticatedUserShouldNotAccessHome() throws Exception {
        mMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void authenticatedUserShouldAccessHome() throws Exception {
        mMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin")
    void adminShouldAccessHome() throws Exception {
        mMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void newVideoFromUnauthorizedEntityShouldFail() throws Exception {
        mMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/new-video")
                                .param("name", "new video name")
                                .param("description", "new video description")
                                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "leno", roles = {"USER"})
    void newVideoFromUserShouldWork() throws Exception {
        mMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/new-video")
                                .param("name", "new video name")
                                .param("description", "new video description")
                                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }
}