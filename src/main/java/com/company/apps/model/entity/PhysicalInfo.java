package com.company.apps.model.entity;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class PhysicalInfo {
    private int weight;
    private int height;
    private String bats;
    private String throwsHand;
}