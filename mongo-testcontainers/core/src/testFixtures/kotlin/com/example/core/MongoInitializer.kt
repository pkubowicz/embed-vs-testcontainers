package com.example.core

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

class MongoInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(context: ConfigurableApplicationContext) {
        val addedProperties = listOf(
            "spring.data.mongodb.uri=${MongoContainerSingleton.instance.replicaSetUrl}"
        )
        TestPropertyValues.of(addedProperties).applyTo(context.environment)
    }
}
