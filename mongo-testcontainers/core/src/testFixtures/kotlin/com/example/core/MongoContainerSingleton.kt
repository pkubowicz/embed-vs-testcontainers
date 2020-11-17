package com.example.core

import org.testcontainers.containers.MongoDBContainer

object MongoContainerSingleton {
    val instance: MongoDBContainer by lazy { startMongoContainer() }

    private fun startMongoContainer(): MongoDBContainer =
        MongoDBContainer("mongo:4.2.10")
            .withReuse(true) // remember to put testcontainers.reuse.enable=true into ~/.testcontainers.properties
            .apply { start() }
}
