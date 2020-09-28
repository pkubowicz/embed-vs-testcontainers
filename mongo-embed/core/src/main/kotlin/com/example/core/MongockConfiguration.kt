package com.example.core

import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.SpringDataMongo3Driver
import com.github.cloudyrock.spring.v5.MongockSpring5
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
class MongockConfiguration {
    @Bean
    fun mongock(mongoTemplate: MongoTemplate, applicationContext: ApplicationContext) =
        MongockSpring5.builder()
            .setDriver(SpringDataMongo3Driver.withDefaultLock(mongoTemplate))
            .addChangeLogsScanPackage("com.example.core.migrations")
            .setSpringContext(applicationContext)
            .buildApplicationRunner()
}
