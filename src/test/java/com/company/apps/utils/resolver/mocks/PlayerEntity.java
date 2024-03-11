package com.company.apps.utils.resolver.mocks;

import com.company.apps.utils.resolver.annotation.CsvColumn;
import com.company.apps.utils.resolver.annotation.CsvEntity;
import com.company.apps.utils.resolver.annotation.IdEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@CsvEntity
@NoArgsConstructor
@AllArgsConstructor
public class PlayerEntity {

    @IdEntity
    @CsvColumn(nameForParsing = "id", defaultValue = "unknown")
    private String id;
}