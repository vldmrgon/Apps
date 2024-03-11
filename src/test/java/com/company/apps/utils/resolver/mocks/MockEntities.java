package com.company.apps.utils.resolver.mocks;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MockEntities {

    public String PLAYER_ENTITY_ID = "1";

    public PlayerEntity playerEntity;

    static {
        playerEntity = PlayerEntity.builder().id(PLAYER_ENTITY_ID).build();
    }
}