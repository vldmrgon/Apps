package com.company.apps.model.entity;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class BirthInfo {
    private int birthYear;
    private int birthMonth;
    private int birthDay;
    private String birthCountry;
    private String birthState;
    private String birthCity;
}