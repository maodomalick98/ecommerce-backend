package com.malick.ecommerce.config;

import com.malick.ecommerce.entity.Country;
import com.malick.ecommerce.entity.Product;
import com.malick.ecommerce.entity.ProductCategory;
import com.malick.ecommerce.entity.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class SpringDataRestConfig implements RepositoryRestConfigurer {

    private final EntityManager entityManager;
    @Autowired
    public SpringDataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] disabledMethods = {HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.POST, HttpMethod.DELETE};

        disableHttpMethods(config.getExposureConfiguration()
                .forDomainType(Product.class), disabledMethods);

        disableHttpMethods(config.getExposureConfiguration()
                .forDomainType(ProductCategory.class), disabledMethods);

        disableHttpMethods(config.getExposureConfiguration()
                .forDomainType(Country.class), disabledMethods);

        disableHttpMethods(config.getExposureConfiguration()
                .forDomainType(State.class), disabledMethods);

        exposeEntityIds(config);
    }

    private void exposeEntityIds(RepositoryRestConfiguration config) {
        Set<EntityType<?>> entities = this.entityManager.getMetamodel().getEntities();
        List<Class> entityClasses = new ArrayList<>();
        for(EntityType entityType: entities) {
            entityClasses.add(entityType.getJavaType());
        }
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }

    private static void disableHttpMethods(ExposureConfigurer config, HttpMethod[] disabledMethods) {
        config
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(disabledMethods
                )).withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(disabledMethods));
    }
}
