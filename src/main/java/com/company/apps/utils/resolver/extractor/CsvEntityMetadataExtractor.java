package com.company.apps.utils.resolver.extractor;

import com.company.apps.utils.resolver.annotation.IdEntity;

import java.lang.reflect.Field;

public class CsvEntityMetadataExtractor<T, ID> implements EntityMetadataExtractor<T, ID> {

    @Override
    public ID extractId(T entity) {
        ID key = null;
        Field[] declaredFields = entity.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(IdEntity.class)) {
                declaredField.setAccessible(true);
                try {
                    key = (ID) declaredField.get(entity);
                    break;
                } catch (Exception e) {
                    throw new RuntimeException("Failed to access @IdEntity field", e);
                }
            }
        }

        if (key == null) {
            throw new IllegalStateException("Entity doesn't have a field marked with @IdEntity");
        }

        return key;
    }
}