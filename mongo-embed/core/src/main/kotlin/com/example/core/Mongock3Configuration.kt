package com.example.core

import com.github.cloudyrock.mongock.SpringBootMongock
import com.github.cloudyrock.mongock.SpringBootMongockBuilder
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
class Mongock3Configuration {
    @Bean
    fun mongock(mongoTemplate: MongoTemplate, applicationContext: ApplicationContext): SpringBootMongock {
        return SpringBootMongockBuilder(mongoTemplate, "com.example.core.migrations")
            .setApplicationContext(applicationContext)
            .setLockQuickConfig()
            .build()
    }
}
