package com.company.apps.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.company.apps.utils.parser.CsvRecordMapper;
import com.company.apps.utils.parser.CsvParser;
import com.company.apps.model.dto.Pageable;
import com.company.apps.model.dto.Page;
import com.company.apps.model.entity.*;

import org.apache.commons.csv.CSVRecord;

import javax.annotation.PostConstruct;

import java.util.function.Function;
import java.util.*;

import java.time.LocalDate;

@Repository
public class PlayerRepository {

    private static final LocalDate DEFAULT_DATE = LocalDate.MIN;
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String DEFAULT_STRING = "unknown";
    private static final int DEFAULT_INTEGER = 0;

    @Value("${player.data.file.path}")
    private String filePath;

    private Map<String, Player> cache;

    @PostConstruct
    public void initRepo() {
        CsvRecordMapper<Player> playerMapper = record -> new Player(
                CsvParser.checkIDFields("playerID", record),

                BirthInfo.builder()
                        .birthYear(CsvParser.parseIntOrDefault("birthYear", DEFAULT_INTEGER, record))
                        .birthMonth(CsvParser.parseIntOrDefault("birthMonth", DEFAULT_INTEGER, record))
                        .birthDay(CsvParser.parseIntOrDefault("birthDay", DEFAULT_INTEGER, record))
                        .birthCountry(CsvParser.parseStringOrDefault("birthCountry", DEFAULT_STRING, record))
                        .birthState(CsvParser.parseStringOrDefault("birthState", DEFAULT_STRING, record))
                        .birthCity(CsvParser.parseStringOrDefault("birthCity", DEFAULT_STRING, record))
                        .build(),

                DeathInfo.builder()
                        .deathYear(CsvParser.parseIntOrDefault("deathYear", DEFAULT_INTEGER, record))
                        .deathMonth(CsvParser.parseIntOrDefault("deathMonth", DEFAULT_INTEGER, record))
                        .deathDay(CsvParser.parseIntOrDefault("deathDay", DEFAULT_INTEGER, record))
                        .deathCountry(CsvParser.parseStringOrDefault("deathCountry", DEFAULT_STRING, record))
                        .deathState(CsvParser.parseStringOrDefault("deathState", DEFAULT_STRING, record))
                        .deathCity(CsvParser.parseStringOrDefault("deathCity", DEFAULT_STRING, record))
                        .build(),

                CsvParser.parseStringOrDefault("nameFirst", DEFAULT_STRING, record),
                CsvParser.parseStringOrDefault("nameLast", DEFAULT_STRING, record),
                CsvParser.parseStringOrDefault("nameGiven", DEFAULT_STRING, record),

                PhysicalInfo.builder()
                        .weight(CsvParser.parseIntOrDefault("weight", DEFAULT_INTEGER, record))
                        .height(CsvParser.parseIntOrDefault("height", DEFAULT_INTEGER, record))
                        .bats(CsvParser.parseStringOrDefault("bats", DEFAULT_STRING, record))
                        .throwsHand(CsvParser.parseStringOrDefault("throwsHand", DEFAULT_STRING, record))
                        .build(),

                CareerInfo.builder()
                        .debut(CsvParser.parseDateOrDefault("debut", DEFAULT_DATE, record, DATE_PATTERN))
                        .finalGame(CsvParser.parseDateOrDefault("finalGame", DEFAULT_DATE, record, DATE_PATTERN))
                        .retroID(CsvParser.parseStringOrDefault("retroID", DEFAULT_STRING, record))
                        .bbrefID(CsvParser.parseStringOrDefault("bbrefID", DEFAULT_STRING, record))
                        .build());

        Function<CSVRecord, String> keyMapper = record -> record.get("playerID");
        cache = CsvParser.parseCsvToMap(filePath, playerMapper, keyMapper);
    }

    public Optional<Player> getById(String id) {
        return Optional.ofNullable(cache.get(id));
    }

    public List<Player> findAll() {
        return List.copyOf(cache.values());
    }

    public List<Player> findAll(int page, int size) {
        page = Math.max(page, 1);
        size = Math.max(size, 1);

        int fromIndex = (page - 1) * size;
        List<Player> allPlayers = List.copyOf(cache.values());

        long totalElements = allPlayers.size();
        int toIndex = Math.min(fromIndex + size, (int) totalElements);

        if (fromIndex >= totalElements) {
            return new ArrayList<>();
        }
        return allPlayers.subList(fromIndex, toIndex);
    }

    public Page<Player> findAll(Pageable pageable) {
        List<Player> players = findAll(pageable.getPage(), pageable.getSize());
        return new Page<>(players, pageable.getPage(), pageable.getSize(), players.size());
    }
}