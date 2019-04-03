package com.basecamp.ice_and_fire.integration;

import com.basecamp.ice_and_fire.IceAndFireApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = IceAndFireApplication.class)
@AutoConfigureMockMvc
public class CharacterControllerIntegrationTest {
    private static final String NAME = "Jon Snow";

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenValidUrl_whenPerformCreateCharacter_thenVerifyResponse() throws Exception {
        //given
        final String URL = "/character";

        //when
        ResultActions result = mvc
                .perform(post(URL)
                        .param("name", NAME));

        //then
        result.andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenValidUrl_whenPerformGetCharacters_thenVerifyResponse() throws Exception {
        //given
        final String URL = "/characters";

        //when
        ResultActions result = mvc
                .perform(get(URL));

        //then
        result.andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk());
    }

}
