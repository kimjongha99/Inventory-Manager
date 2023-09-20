package com.springboot.inventory.common.config;

//
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;

//
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDSLConfiguration {

    @PersistenceContext
    EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

}
