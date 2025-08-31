package io.jp;

import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

public class DatabaseTestContainer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final MySQLContainer DB_CONTAINER = new MySQLContainer("mysql:5.7.34");
    private static final Logger log = LoggerFactory.getLogger(DatabaseTestContainer.class);

    static {
        Startables.deepStart(DB_CONTAINER).join();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.info("Starting DatabaseTestContainer {}", DB_CONTAINER.getJdbcUrl());
        TestPropertyValues.of(
                "spring.datasource.url=" + DB_CONTAINER.getJdbcUrl(),
                "spring.datasource.username=" + DB_CONTAINER.getUsername(),
                "spring.datasource.password=" + DB_CONTAINER.getPassword(),
                "spring.liquibase.url=" + DB_CONTAINER.getJdbcUrl(),
                "spring.liquibase.user=" + DB_CONTAINER.getUsername(),
                "spring.liquibase.password=" + DB_CONTAINER.getPassword()
        ).applyTo(applicationContext.getEnvironment());
    }
}
