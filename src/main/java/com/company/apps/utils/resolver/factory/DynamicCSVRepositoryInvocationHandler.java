package com.company.apps.utils.resolver.factory;

import com.company.apps.utils.resolver.annotation.IdEntity;
import com.company.apps.repository.CSVRepository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

public class DynamicCSVRepositoryInvocationHandler<T, ID> implements CSVRepository<T, ID>, InvocationHandler {

    private final Class<T> entityClass;
    private final Class<ID> idClass;
    ConcurrentHashMap cache;

    public DynamicCSVRepositoryInvocationHandler(Class<T> entityClass, Class<ID> idClass) {
        this.entityClass = entityClass;
        this.idClass = idClass;
        cache = new ConcurrentHashMap<ID, T>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        return switch (method.getName()) {

            case "save" -> save((T) args[0]);

            case "findAll" -> args.length == 0 ? findAll() : findAll((int) args[0], (int) args[1]);

            case "findById" -> findById((ID) args[0]);

            case "deleteById" -> {
                deleteById((ID) args[0]);
                yield null;
            }

            case "deleteAll" -> {
                deleteAll();
                yield null;
            }

            default -> throw new IllegalStateException("Unexpected value: " + method.getName());
        };
    }

    @Override
    public T save(T entity) {
        ID key = getIdEntity(entity);
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

    private ID getIdEntity(T entity) {
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