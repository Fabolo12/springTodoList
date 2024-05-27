package org.example.springprojecttodo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.sql.DataSource;

@Configuration
public class MyConfiguration {

    private final ConfigurableEnvironment environment;

    public MyConfiguration(final ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        environment.setActiveProfiles("map");
        return DataSourceBuilder
                .create()
                .url("jdbc:postgresql://localhost:5432/test")
                .username("postgres")
                .password("root")
                .build();
    }


}
