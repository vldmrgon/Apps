package com.company.apps.utils.convertor;

import com.company.apps.model.dto.PlayerDTO;
import com.company.apps.model.entity.Player;
import com.company.apps.object.Mocks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerConvertorTest {
    private PlayerConvertor playerConvertor;

    @BeforeEach
    void setUp() {
        playerConvertor = new PlayerConvertor();
    }

    @Test
    void playerEntityToPlayerDTOConvertorTest() {
        Player player = Mocks.player;
        PlayerDTO result = playerConvertor.convertToDTO(player);

        Assertions.assertEquals(Mocks.PLAYER_ID, result.getPlayerID());
        Assertions.assertEquals(Mocks.birthInfo, result.getBirthInfo());
        Assertions.assertEquals(Mocks.deathInfo, result.getDeathInfo());
        Assertions.assertEquals(Mocks.NAME_FIRST, result.getNameFirst());
        Assertions.assertEquals(Mocks.NAME_LAST, result.getNameLast());
        Assertions.assertEquals(Mocks.NAME_GIVEN, result.getNameGiven());
        Assertions.assertEquals(Mocks.physicalInfo, result.getPhysicalInfo());
        Assertions.assertEquals(Mocks.careerInfo, result.getCareerInfo());
    }
}