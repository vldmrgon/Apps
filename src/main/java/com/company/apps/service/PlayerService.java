package com.company.apps.service;

import com.company.apps.exception.PlayerBusinessException;
import com.company.apps.utils.convertor.PlayerConvertor;
import com.company.apps.repository.PlayerRepository;
import com.company.apps.model.dto.PlayerDTO;
import com.company.apps.model.entity.Player;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerConvertor playerConvertor;

    public List<PlayerDTO> getAllPlayers() {
        return playerRepository
                .findAll()
                .stream()
                .map(playerConvertor::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PlayerDTO> getAllPlayers(int page, int size) {
        return playerRepository.findAll(page, size)
                .stream()
                .map(playerConvertor::convertToDTO)
                .collect(Collectors.toList());
    }

    public PlayerDTO getPlayerById(String id) {
        Player player = playerRepository.getById(id)
                .orElseThrow(() -> new PlayerBusinessException("The player with id: " + id + " not found"));
        return playerConvertor.convertToDTO(player);
    }
}