package com.oriontek.clients.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(
        basePackages = {
                "com.oriontek.clients.command.repository",
                "com.oriontek.clients.outbox"
        },
        entityManagerFactoryRef = "writeEmf",
        transactionManagerRef = "writeTx")
public class WriteDataSourceConfig {
    
@Primary
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties writeDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource writeDataSource(
            @Qualifier("writeDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(initMethod = "migrate")
    public Flyway writeFlyway(@Qualifier("writeDataSource") DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration/write")
                .load();
    }

    @Primary
    @Bean(name = "writeEmf")
    @DependsOn("writeFlyway")
    public LocalContainerEntityManagerFactoryBean writeEmf(
            EntityManagerFactoryBuilder builder,
            @Qualifier("writeDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.oriontek.clients.command.domain", "com.oriontek.clients.outbox")
                .persistenceUnit("write")
                .build();
    }

    @Primary
    @Bean(name = "writeTx")
    public PlatformTransactionManager writeTx(
            @Qualifier("writeEmf") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
