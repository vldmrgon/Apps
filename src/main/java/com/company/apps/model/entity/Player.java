package com.company.apps.model.entity;

import com.company.apps.utils.resolver.annotation.CsvNestedEntity;
import com.company.apps.utils.resolver.annotation.CsvColumn;
import com.company.apps.utils.resolver.annotation.CsvEntity;
import com.company.apps.utils.resolver.annotation.IdEntity;
import lombok.*;

@Getter
@Setter
@Builder
@CsvEntity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @IdEntity
    @CsvColumn(nameForParsing = "playerID")
    private String playerID;

    @CsvNestedEntity
    private BirthInfo birthInfo;

    @CsvNestedEntity
    private DeathInfo deathInfo;

    @CsvColumn(nameForParsing = "nameFirst", defaultValue = "unknown")
    private String nameFirst;

    @CsvColumn(nameForParsing = "nameLast", defaultValue = "unknown")
    private String nameLast;

    @CsvColumn(nameForParsing = "nameLast", defaultValue = "unknown")
    private String nameGiven;

    @CsvNestedEntity
    private PhysicalInfo physicalInfo;

    @CsvNestedEntity
    private CareerInfo careerInfo;
}