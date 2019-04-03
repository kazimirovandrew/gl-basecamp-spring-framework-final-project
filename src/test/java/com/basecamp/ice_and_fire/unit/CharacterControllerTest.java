package com.basecamp.ice_and_fire.unit;

import com.basecamp.ice_and_fire.controller.CharacterController;
import com.basecamp.ice_and_fire.service.CharacterService;
import com.basecamp.ice_and_fire.wire.CreateCharacterResponse;
import com.basecamp.ice_and_fire.wire.GetCharacterByIdResponse;
import com.basecamp.ice_and_fire.wire.GetCharactersResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CharacterController.class)
public class CharacterControllerTest {
    private static final String NAME = "Jon Snow";
    private static final UUID ID = UUID.randomUUID();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CharacterService characterService;

    @Test
    public void givenValidUrl_whenPerformCreateCharacter_thenVerifyResponse() throws Exception {
        final String VALUE = ID.toString();

        CreateCharacterResponse response = new CreateCharacterResponse();
        response.setId(ID);

        given(characterService.createCharacter(NAME)).willReturn(response);

        //given
        final String URL = "/character";

        //when
        ResultActions result = mvc
                .perform(post(URL)
                        .param("name", NAME));

        //then
        result.andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(VALUE));
    }

    @Test
    public void givenInvalidParameter_whenPerformCreateCharacter_thenVerifyExceptionMessage() throws Exception {
        final String VALUE = "Name must not be blank!";

        //given
        final String URL = "/character";

        //when
        ResultActions result = mvc
                .perform(post(URL)
                        .param("name", ""));

        //then
        result.andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value(VALUE));
    }

    @Test
    public void givenValidUrl_whenPerformGetCharacterById_thenVerifyResponse() throws Exception {
        final String VALUE = "Jon Snow is a homeless";

        GetCharacterByIdResponse response = new GetCharacterByIdResponse();
        response.setMessage(NAME, new ArrayList<>());

        given(characterService.getCharacterById(ID)).willReturn(response);

        //given
        final String URL = "/characters/" + ID;

        //when
        ResultActions result = mvc
                .perform(get(URL));

        //then
        result.andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(VALUE));
    }

    @Test
    public void givenInvalidParameter_whenPerformGetCharacterById_thenVerifyExceptionMessage() throws Exception {
        final String VALUE = "Invalid id length!";

        //given
        final String URL = "/characters/0";

        //when
        ResultActions result = mvc
                .perform(get(URL));

        //then
        result.andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value(VALUE));
    }

    @Test
    public void givenValidUrl_whenPerformGetCharacters_thenVerifyResponse() throws Exception {
        final String EXPRESSION = "$.nameToId.['" + NAME + "']";
        final String VALUE = ID.toString();
        final Map<String, UUID> map = new HashMap<>();
        map.put(NAME, ID);

        GetCharactersResponse response = new GetCharactersResponse();
        response.setNameToId(map);

        given(characterService.getCharacters()).willReturn(response);

        //given
        final String URL = "/characters";

        //when
        ResultActions result = mvc
                .perform(get(URL));

        //then
        result.andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(EXPRESSION)
                        .value(VALUE));
    }
}
