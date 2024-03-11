package com.company.apps.utils.resolver.factory;


import com.company.apps.utils.resolver.extractor.CsvEntityMetadataExtractor;
import com.company.apps.utils.resolver.extractor.EntityMetadataExtractor;
import com.company.apps.utils.resolver.cache.CsvCache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicCSVRepositoryInvocationHandler<T, ID> implements CSVRepository<T, ID>, InvocationHandler {

    private final EntityMetadataExtractor<T, ID> extractor;
    private final ConcurrentHashMap<ID, T> cache;
    private final Class<T> entityClass;
    private final Class<ID> idClass;

    public DynamicCSVRepositoryInvocationHandler(Class<T> entityClass, Class<ID> idClass) {
        this.entityClass = entityClass;
        this.idClass = idClass;
        this.cache = new CsvCache<ID, T>().getCache();
        this.extractor = new CsvEntityMetadataExtractor<>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        return switch (method.getName()) {

            case "save" -> save((T) args[0]);

            case "findAll" -> {

                if (args == null || args.length == 0) {
                    yield findAll();
                } else {
                    yield findAll((int) args[0], (int) args[1]);
                }
            }

            case "findById" -> findById((ID) args[0]);

            case "deleteById" -> {
                deleteById((ID) args[0]);
                yield null;
            }

            case "deleteAll" -> {
                deleteAll();
                yield null;
            }

            default -> throw new IllegalStateException("Unexpected method or method values: " + method.getName());
        };
    }

    @Override
    public T save(T entity) {
        ID key = extractor.extractId(entity);
        return (T) cache.put(key, entity);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<T>(cache.values());
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable((T) cache.get(id));
    }

    @Override
    public List<T> findAll(int page, int size) {
        page = Math.max(page, 1);
        size = Math.max(size, 1);

        int fromIndex = (page - 1) * size;
        List<T> allObjects = new ArrayList<T>(cache.values());

        long totalElements = allObjects.size();
        int toIndex = Math.min(fromIndex + size, (int) totalElements);

        if (fromIndex >= totalElements) {
            return new ArrayList<>();
        }
        return allObjects.subList(fromIndex, toIndex);
    }

    @Override
    public void deleteById(ID id) {
        cache.remove(id);
    }

    @Override
    public void deleteAll() {
        cache.clear();
    }
}