package com.company.apps.model.entity;

import com.company.apps.utils.resolver.annotation.IdEntity;
import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
//@CsvEntity(repositoryClass = PlayerRepository.class)
public class Player {

    //    @CsvColumn(nameForParsing = "playerID")
    @IdEntity
    private String playerID;

    //    @CsvNestedEntity
    private BirthInfo birthInfo;

    //    @CsvNestedEntity
    private DeathInfo deathInfo;

    //    @CsvColumn(nameForParsing = "nameFirst", defaultValue = "unknown")
    private String nameFirst;

    //    @CsvColumn(nameForParsing = "nameLast", defaultValue = "unknown")
    private String nameLast;

    //    @CsvColumn(nameForParsing = "nameLast", defaultValue = "unknown")
    private String nameGiven;

    //    @CsvNestedEntity
    private PhysicalInfo physicalInfo;

    //    @CsvNestedEntity
    private CareerInfo careerInfo;
}