package com.company.apps.utils.resolver.factory;

import com.company.apps.utils.resolver.extractor.EntityMetadataExtractor;
import com.company.apps.utils.resolver.mocks.MockEntities;
import com.company.apps.utils.resolver.mocks.PlayerEntity;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Mock;

import java.util.stream.IntStream;
import java.util.Optional;
import java.util.List;

import java.lang.reflect.Method;

@ExtendWith(MockitoExtension.class)
class DynamicCSVRepositoryInvocationHandlerTest {

    @Mock
    private EntityMetadataExtractor<PlayerEntity, String> extractor;

    @InjectMocks
    private final DynamicCSVRepositoryInvocationHandler<PlayerEntity, String> handler =
            new DynamicCSVRepositoryInvocationHandler<>(PlayerEntity.class, String.class);

    @BeforeEach
    void setUp() {
        Mockito
                .lenient()
                .when(extractor.extractId(ArgumentMatchers.any(PlayerEntity.class)))
                .thenReturn(MockEntities.PLAYER_ENTITY_ID);
    }

    private void saveInvokeEntity(PlayerEntity entity) throws Exception {
        Method saveMethod = CSVRepository.class.getMethod("save", Object.class);
        handler.invoke(null, saveMethod, new Object[]{entity});
    }

    private <T> T invokeMethodWithoutArgs(Method method) throws Exception {
        return invokeMethodWithArgs(method);
    }

    private <T> T invokeMethodWithArgs(Method method, Object... args) throws Exception {
        @SuppressWarnings("unchecked")
        T result = (T) handler.invoke(null, method, args);
        return result;
    }

    private PlayerEntity createPlayerEntity(String id) {
        return PlayerEntity.builder().id(id).build();
    }

    @Nested
    class DirectMethodCalls {

        @Test
        void saveMethodTest() {
            PlayerEntity entity = MockEntities.playerEntity;
            handler.save(entity);

            Optional<PlayerEntity> result = handler.findById(MockEntities.PLAYER_ENTITY_ID);

            Assertions.assertTrue(result.isPresent());
            Assertions.assertEquals(entity, result.get());
            Assertions.assertEquals(MockEntities.PLAYER_ENTITY_ID, result.get().getId());
        }

        @Test
        void findAllMethodTest() {
            PlayerEntity entity1 = createPlayerEntity("1");
            PlayerEntity entity2 = createPlayerEntity("2");

            handler.save(entity1);
            handler.save(entity2);

            List<PlayerEntity> result = handler.findAll();

            Assertions.assertNotNull(result);
            Assertions.assertEquals(2, result.size());
        }

        @Test
        void findByIdMethodTest() {
            PlayerEntity entity = MockEntities.playerEntity;
            handler.save(entity);

            Optional<PlayerEntity> result = handler.findById(MockEntities.PLAYER_ENTITY_ID);

            Assertions.assertTrue(result.isPresent());
            Assertions.assertEquals(entity, result.get());
            Assertions.assertEquals(MockEntities.PLAYER_ENTITY_ID, result.get().getId());
        }

        @Test
        void deleteByIdMethodTest() {
            PlayerEntity entity = MockEntities.playerEntity;
            handler.save(entity);

            handler.deleteById(MockEntities.PLAYER_ENTITY_ID);
            Optional<PlayerEntity> result = handler.findById(MockEntities.PLAYER_ENTITY_ID);

            Assertions.assertFalse(result.isPresent());
        }

    }

    @Nested
    class InvokeMethodCalls {


        @Test
        void invokeSaveTest() throws Exception {
            PlayerEntity entity = createPlayerEntity(MockEntities.PLAYER_ENTITY_ID);
            saveInvokeEntity(entity);

            Assertions.assertTrue(handler.findById(entity.getId()).isPresent());
        }

        @Test
        void invokeFindAllTest() throws Exception {
            PlayerEntity entity1 = createPlayerEntity(MockEntities.PLAYER_ENTITY_ID);
            PlayerEntity entity2 = createPlayerEntity("2");

            saveInvokeEntity(entity1);
            saveInvokeEntity(entity2);

            Method findAllMethod = CSVRepository.class.getMethod("findAll");
            List<PlayerEntity> result = invokeMethodWithoutArgs(findAllMethod);

            Assertions.assertEquals(2, result.size());
        }

        @Test
        void invokeFindAllWithPaginationTest() throws Exception {

            Method findAllPagedMethod = CSVRepository.class.getMethod("findAll", int.class, int.class);

            IntStream.rangeClosed(1, 5).forEach(i -> {
                try {
                    PlayerEntity entity = createPlayerEntity(String.valueOf(i));
                    saveInvokeEntity(entity); // Используйте saveEntity для сохранения сущности
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            List<PlayerEntity> page1 = invokeMethodWithArgs(findAllPagedMethod, 1, 3);
            Assertions.assertEquals(3, page1.size(), "Page 1 should have 3 entities");

            List<PlayerEntity> page2 = invokeMethodWithArgs(findAllPagedMethod, 2, 3);
            Assertions.assertEquals(2, page2.size(), "Page 2 should have 2 entities because there are only 5 entities in total");
        }

        @Test
        void invokeDeleteByIdTest() throws Exception {
            PlayerEntity entity1 = createPlayerEntity(MockEntities.PLAYER_ENTITY_ID);
            PlayerEntity entity2 = createPlayerEntity("2");

            saveInvokeEntity(entity1);
            saveInvokeEntity(entity2);

            Method deleteByIdMethod = CSVRepository.class.getMethod("deleteById", Object.class);
            handler.invoke(null, deleteByIdMethod, new Object[]{MockEntities.PLAYER_ENTITY_ID});

            Assertions.assertNull(handler.findById(MockEntities.PLAYER_ENTITY_ID).orElse(null));
            Assertions.assertNotNull(handler.findById("2").orElse(null));
        }

        @Test
        void invokeDeleteAllTest() throws Exception {
            saveInvokeEntity(createPlayerEntity(MockEntities.PLAYER_ENTITY_ID));
            saveInvokeEntity(createPlayerEntity("2"));

            Method deleteAllMethod = CSVRepository.class.getMethod("deleteAll");
            invokeMethodWithoutArgs(deleteAllMethod);

            Assertions.assertTrue(handler.findAll().isEmpty());
        }
    }
}