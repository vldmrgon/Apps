package com.company.apps.model.entity;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class Player {
    private String playerID;
    private BirthInfo birthInfo;
    private DeathInfo deathInfo;
    private String nameFirst;
    private String nameLast;
    private String nameGiven;
    private PhysicalInfo physicalInfo;
    private CareerInfo careerInfo;
}