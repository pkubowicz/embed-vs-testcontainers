package com.example.core

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.security.Principal

@RestController
class UserController(private val userRepository: UserRepository) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/user")
    fun getAll(principal: Principal): Flux<User> {
        logger.info("Handling for {}", principal)
        return userRepository.findAll()
    }
}
