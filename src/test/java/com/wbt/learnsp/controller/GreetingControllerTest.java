package com.wbt.learnsp.controller;

import com.wbt.learnsp.model.NewVideo;
import com.wbt.learnsp.service.VideoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@WebMvcTest(controllers = {GreetingController.class})
class GreetingControllerTest {

    @Autowired
    MockMvc mMvc;

    @MockBean
    VideoService mVideoService;

    @Test
    @WithMockUser
    void indexPageHasSeveralHtmlForms() throws Exception {
        String htmlContent = mMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Username: user")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Authorities: [ROLE_USER]")))
                .andReturn()
                .getResponse().getContentAsString();

        assertThat(htmlContent).contains(
                "<form method=\"post\" action=\"/new-video\">",
                "<form action=\"/logout\" method=\"post\">");
    }

    @Test
    @WithMockUser
    void postNewVideoShouldWork() throws Exception {
        mMvc.perform(
                        MockMvcRequestBuilders.post("/new-video")
                                .with(SecurityMockMvcRequestPostProcessors.csrf()) //can also provide jwt
                                .param("name", "new-video")
                                .param("description", "new-description")
                )
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));

        // verify that create method has been called with these parameters
        verify(mVideoService).create(new NewVideo("new-video", "new-description"), "user");
    }
}