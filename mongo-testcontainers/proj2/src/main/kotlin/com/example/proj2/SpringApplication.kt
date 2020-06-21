package com.example.proj2

import com.example.core.CoreConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@Import(CoreConfiguration::class)
@EnableReactiveMongoRepositories(
    basePackages = [
        "com.example.proj2"
    ]
)
class SpringApplication

fun main(args: Array<String>) {
    runApplication<SpringApplication>(*args)
}
