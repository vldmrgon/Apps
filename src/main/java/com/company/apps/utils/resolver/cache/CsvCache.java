package com.company.apps.utils.resolver.cache;

import java.util.concurrent.ConcurrentHashMap;

public class CsvCache<ID, T> implements CacheManager<ID, T> {

    @Override
    public ConcurrentHashMap<ID, T> getCache() {
        return new ConcurrentHashMap<ID, T>();
    }
}