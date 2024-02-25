package com.company.apps.utils.convertor;

import org.springframework.stereotype.Component;
import com.company.apps.model.dto.PlayerDTO;
import com.company.apps.model.entity.Player;

@Component
public class PlayerConvertor implements Mapping<Player, PlayerDTO> {

    @Override
    public PlayerDTO convertToDTO(Player player) {
        return PlayerDTO.builder()
                .playerID(player.getPlayerID())
                .birthInfo(player.getBirthInfo())
                .deathInfo(player.getDeathInfo())
                .nameFirst(player.getNameFirst())
                .nameLast(player.getNameLast())
                .nameGiven(player.getNameGiven())
                .physicalInfo(player.getPhysicalInfo())
                .careerInfo(player.getCareerInfo())
                .build();
    }
}