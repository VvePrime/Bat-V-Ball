package com.vivek.batvball.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
    @Autowired
    DataSource dataSource;
    
    @Autowired
    EntityManagerFactory entityManagerFactory;
    
    @Bean
    PlatformTransactionManager transactionManager() {
       return new JpaTransactionManager(entityManagerFactory);
    }
    
    @Bean
    JdbcTemplate jdbcTemplate() {
    	return new JdbcTemplate(dataSource);
    }

}
