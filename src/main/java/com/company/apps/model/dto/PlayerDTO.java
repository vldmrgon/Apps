package com.company.apps.model.dto;

import com.company.apps.model.entity.PhysicalInfo;
import com.company.apps.model.entity.CareerInfo;
import com.company.apps.model.entity.BirthInfo;
import com.company.apps.model.entity.DeathInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlayerDTO {
    private String playerID;
    private BirthInfo birthInfo;
    private DeathInfo deathInfo;
    private String nameFirst;
    private String nameLast;
    private String nameGiven;
    private PhysicalInfo physicalInfo;
    private CareerInfo careerInfo;
}