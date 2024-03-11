package com.company.apps.utils.resolver.extractor;

public interface EntityMetadataExtractor<T, ID> {

    ID extractId(T entity);
}