package com.company.apps.utils.convertor;

public interface Mapping<ENTITY, DTO> {

    DTO convertToDTO (ENTITY entity);
}