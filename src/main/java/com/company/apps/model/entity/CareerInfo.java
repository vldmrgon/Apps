package com.company.apps.model.entity;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class CareerInfo {
    private LocalDate debut;
    private LocalDate finalGame;
    private String retroID;
    private String bbrefID;
}