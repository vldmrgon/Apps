package com.company.apps.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.company.apps.service.PlayerService;
import com.company.apps.model.dto.PlayerDTO;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/players")
    public ResponseEntity<List<PlayerDTO>> getPlayers() {
        List<PlayerDTO> allPlayers = playerService.getAllPlayers();
        return ResponseEntity.status(HttpStatus.OK).body(allPlayers);
    }

    @GetMapping("/players/paged")
    public ResponseEntity<List<PlayerDTO>> getPlayersPaged(@RequestParam("page") int page, @RequestParam("size") int size) {
        List<PlayerDTO> playersPage = playerService.getAllPlayers(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(playersPage);
    }

    @GetMapping("/players/{playerID}")
    public ResponseEntity<PlayerDTO> getPlayer(@PathVariable String playerID) {
        PlayerDTO playerById = playerService.getPlayerById(playerID);
        return ResponseEntity.status(HttpStatus.OK).body(playerById);
    }
}