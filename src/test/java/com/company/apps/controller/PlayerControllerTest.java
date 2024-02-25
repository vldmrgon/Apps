package com.company.apps.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.apps.service.PlayerService;
import com.company.apps.model.dto.PlayerDTO;
import com.company.apps.object.Mocks;

import org.mockito.ArgumentMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerTest {

    private static final String BASE_URL = "/api/v1/players";
    private static final String PAGED_URL = BASE_URL + "/paged";
    private static final String PLAYER_BY_ID_URL = BASE_URL + "/{playerID}";

    @MockBean
    private PlayerService playerService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllPlayersControllerTest() throws Exception {
        List<PlayerDTO> mockPlayerList = Collections.singletonList(Mocks.playerDTO);

        Mockito
                .when(playerService.getAllPlayers())
                .thenReturn(mockPlayerList);

        mockMvc
                .perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockPlayerList)));
    }

    @Test
    void getAllPlayersPagedControllerTest() throws Exception {
        List<PlayerDTO> mockPlayerDTO = Collections.singletonList(Mocks.playerDTO);
        int page = 0, size = 5;

        Mockito
                .when(playerService.getAllPlayers(page, size))
                .thenReturn(mockPlayerDTO);

        mockMvc
                .perform(MockMvcRequestBuilders.get(PAGED_URL)
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockPlayerDTO)));
    }

    @Test
    void getPlayerByIDControllerTest() throws Exception {
        PlayerDTO mockPlayerDTO = Mocks.playerDTO;

        Mockito
                .when(playerService.getPlayerById(ArgumentMatchers.anyString()))
                .thenReturn(mockPlayerDTO);

        mockMvc
                .perform(MockMvcRequestBuilders.get(PLAYER_BY_ID_URL, Mocks.PLAYER_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockPlayerDTO)));
    }
}