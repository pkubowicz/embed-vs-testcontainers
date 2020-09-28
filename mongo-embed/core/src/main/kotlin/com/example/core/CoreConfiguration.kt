package com.example.core

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import java.time.Clock

@Configuration
@ComponentScan(
    basePackages = [
        "com.example.core"
    ]
)
@EnableReactiveMongoRepositories(
    basePackages = [
        "com.example.core"
    ]
)
@Import(MongockConfiguration::class)
class CoreConfiguration {

    @Bean
    fun clock(): Clock = Clock.systemUTC()
}
