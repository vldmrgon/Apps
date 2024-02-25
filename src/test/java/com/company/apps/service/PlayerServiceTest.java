package com.company.apps.service;

import com.company.apps.exception.PlayerBusinessException;
import com.company.apps.utils.convertor.PlayerConvertor;
import com.company.apps.repository.PlayerRepository;
import com.company.apps.model.dto.PlayerDTO;
import com.company.apps.model.entity.Player;
import com.company.apps.object.Mocks;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Mock;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private PlayerConvertor playerConvertor;
    @InjectMocks
    private PlayerService playerService;

    @Test
    void findAllPlayersServiceTest() {
        Mockito
                .when(playerRepository.findAll())
                .thenReturn(Collections.singletonList(Mocks.player));

        Mockito
                .when(playerConvertor.convertToDTO(ArgumentMatchers.any(Player.class)))
                .thenReturn(Mocks.playerDTO);

        List<PlayerDTO> result = playerService.getAllPlayers();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(Mocks.playerDTO, result.get(0));
    }

    @Test
    void findPlayerByIdServiceTest() {
        Mockito
                .when(playerRepository.getById(Mocks.PLAYER_ID))
                .thenReturn(Optional.of(Mocks.player));

        Mockito
                .when(playerConvertor.convertToDTO(Mocks.player))
                .thenReturn(Mocks.playerDTO);

        PlayerDTO result = playerService.getPlayerById(Mocks.PLAYER_ID);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(Mocks.playerDTO, result);
    }

    @Test
    void findPlayerByIdNotFoundServiceTest() {
        Mockito
                .when(playerRepository.getById("unknown"))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(PlayerBusinessException.class, () -> playerService.getPlayerById("unknown"));
    }
}