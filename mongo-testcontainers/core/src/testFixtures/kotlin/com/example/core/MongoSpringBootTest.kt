package com.example.core

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import kotlin.annotation.AnnotationTarget.CLASS

@Target(CLASS)
@SpringBootTest
@ContextConfiguration(initializers = [MongoInitializer::class])
annotation class MongoSpringBootTest
