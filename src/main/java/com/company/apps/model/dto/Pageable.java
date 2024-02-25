package com.company.apps.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pageable {
    int page;
    int size;
}