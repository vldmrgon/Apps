package com.company.apps.object;

import com.company.apps.model.dto.PlayerDTO;
import com.company.apps.model.dto.Pageable;
import lombok.experimental.UtilityClass;
import com.company.apps.model.entity.*;

import java.time.LocalDate;

@UtilityClass
public class Mocks {

    public String PLAYER_ID = "player-1";
    public String NAME_FIRST = "John";
    public String NAME_LAST = "Doe";
    public String NAME_GIVEN = "Friend";

    public LocalDate BIRTH_DATE = LocalDate.of(1960, 1, 1);
    public String BIRTH_COUNTRY = "USA";
    public String BIRTH_CITY = "Buffalo";
    public String BIRTH_STATE = "New York";

    public LocalDate CAREER_DEBUT = LocalDate.of(1980, 01, 01);
    public LocalDate CAREER_FINAL = LocalDate.of(2000, 10, 10);
    public String RETRO_ID = "ardd001";
    public String BBREF_ID = "aardsda01";

    public Integer DEATH_YEAR = 2020;
    public Integer DEATH_MONTH = 10;
    public Integer DEATH_DAY = 10;
    public String DEATH_COUNTRY = "Canada";
    public String DEATH_CITY = "Toronto";
    public String DEATH_STATE = "Ontario";

    public int WEIGHT = 80;
    public int HEIGHT = 180;
    public String BATS = "L";
    public String THROWS_HAND = "R";

    public PhysicalInfo physicalInfo;
    public CareerInfo careerInfo;
    public BirthInfo birthInfo;
    public DeathInfo deathInfo;
    public Player player;

    public PlayerDTO playerDTO;
    public Pageable pageable;

    static {

        // FIXME: Attention! If there is an error here, please, check all mutable object imports,
        //  before making major changes (cursor on the object Shift+Alt+7)

        birthInfo = new BirthInfo(
                BIRTH_DATE.getYear(),
                BIRTH_DATE.getMonthValue(),
                BIRTH_DATE.getDayOfMonth(),
                BIRTH_COUNTRY,
                BIRTH_STATE,
                BIRTH_CITY
        );

        careerInfo = new CareerInfo(CAREER_DEBUT, CAREER_FINAL, RETRO_ID, BBREF_ID);

        deathInfo = new DeathInfo(DEATH_YEAR, DEATH_MONTH, DEATH_DAY, DEATH_COUNTRY, DEATH_STATE, DEATH_CITY);

        physicalInfo = new PhysicalInfo(WEIGHT, HEIGHT, BATS, THROWS_HAND);

        player = new Player(PLAYER_ID, birthInfo, deathInfo, NAME_FIRST, NAME_LAST, NAME_GIVEN, physicalInfo, careerInfo);

        playerDTO = new PlayerDTO(PLAYER_ID, birthInfo, deathInfo, NAME_FIRST, NAME_LAST, NAME_GIVEN, physicalInfo, careerInfo);

        pageable = new Pageable(1, 10);
    }
}