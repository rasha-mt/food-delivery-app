package com.mentorship.food_delivery_app.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;


@Configuration
@Profile("test")

public class H2FlywayConfig {

    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource dataSource) {

        return Flyway.configure()
                .dataSource(dataSource)
                .locations(
                        "classpath:src/test/resources/db/migration/db/migration"
                )
                .validateOnMigrate(false)
                .outOfOrder(true)
                .cleanDisabled(false)          // ✅ allow clean
             //   .cleanOnValidationError(true)  // ✅ auto clean on checksum mismatch
                .load();
    }
}