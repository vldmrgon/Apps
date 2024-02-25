package com.company.apps.repository;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.company.apps.model.entity.Player;
import com.company.apps.model.dto.Pageable;
import com.company.apps.object.Mocks;

import java.lang.reflect.Field;

import lombok.SneakyThrows;

import java.util.Optional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class PlayerRepositoryTest {

    @InjectMocks
    private PlayerRepository playerRepository;
    private final Map<String, Player> testCache = new HashMap<>();

    @BeforeEach
    void setUp() {
        playerRepository = new PlayerRepository();
        testCache.put(Mocks.PLAYER_ID, Mocks.player);
        setInternalState(playerRepository, "cache", testCache);
    }

    @Test
    void getByIdRepositoryTestTest() {
        Optional<Player> result = playerRepository.getById(Mocks.PLAYER_ID);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(Mocks.PLAYER_ID, result.get().getPlayerID());
    }

    @Test
    void findAllPlayersRepositoryTest() {
        List<Player> result = playerRepository.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(Mocks.PLAYER_ID, result.get(0).getPlayerID());
    }

    @Test
    void findAllWithPaginationRepositoryTest() {
        Pageable pageable = Mocks.pageable;
        List<Player> result = playerRepository.findAll(pageable.getPage(), pageable.getSize());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(Mocks.PLAYER_ID, result.get(0).getPlayerID());
    }

    @SneakyThrows
    private void setInternalState(Object target, String fieldName, Object value) {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}