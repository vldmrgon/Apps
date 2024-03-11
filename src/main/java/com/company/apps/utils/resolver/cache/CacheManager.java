package com.company.apps.utils.resolver.cache;

import java.util.concurrent.ConcurrentHashMap;

public interface CacheManager<ID, T> {

    ConcurrentHashMap<ID, T> getCache();
}
