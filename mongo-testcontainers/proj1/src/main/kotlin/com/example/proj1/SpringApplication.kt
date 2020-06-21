package com.example.proj1

import com.example.core.CoreConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@Import(CoreConfiguration::class)
@EnableReactiveMongoRepositories(
    basePackages = [
        "com.example.proj1"
    ]
)
class SpringApplication

fun main(args: Array<String>) {
    runApplication<SpringApplication>(*args)
}
