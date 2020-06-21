package com.example.core

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(CoreConfiguration::class)
class TestSpringApplication {
}
