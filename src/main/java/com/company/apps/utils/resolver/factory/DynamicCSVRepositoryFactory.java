package com.company.apps.utils.resolver.factory;

import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.BeansException;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.util.StringUtils;

import org.reflections.util.ConfigurationBuilder;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.Reflections;

import java.util.stream.Collectors;
import java.lang.reflect.Proxy;
import java.util.Arrays;

@Configuration
public class DynamicCSVRepositoryFactory implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        String basicNamePackage = Arrays
                .stream(DynamicCSVRepositoryFactory.class.getPackage().getName().split("\\."))
                .limit(3)
                .collect(Collectors.joining("."));

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(basicNamePackage)
                .setScanners(new SubTypesScanner()));

        var subTypes = reflections.getSubTypesOf(CSVRepository.class);
        subTypes.remove(DynamicCSVRepositoryInvocationHandler.class);

        if (subTypes.isEmpty()) return;

        for (var subType : subTypes) {

            ResolvableType resolvableType = ResolvableType.forClass(subType);
            ResolvableType superType = resolvableType.as(CSVRepository.class);

            var entityType = superType.getGeneric(0).resolve();
            var idType = superType.getGeneric(1).resolve();

            String simpleName = subType.getSimpleName();
            String beanName = StringUtils.uncapitalize(simpleName);

            registerCSVRepositoryBean(registry, beanName, entityType, idType, subType);
        }
    }

    private <T, ID> void registerCSVRepositoryBean(BeanDefinitionRegistry registry,
                                                   String beanName,
                                                   Class<T> entityClass,
                                                   Class<ID> idClass,
                                                   Class<? extends CSVRepository> repositoryInterface) {

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();

        beanDefinition.setBeanClass(repositoryInterface);
        beanDefinition.setInstanceSupplier(
                () -> Proxy
                        .newProxyInstance(
                                CSVRepository.class.getClassLoader(),
                                new Class<?>[]{repositoryInterface},
                                new DynamicCSVRepositoryInvocationHandler<>(entityClass, idClass)
                        )
        );

        registry.registerBeanDefinition(beanName, beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }
}