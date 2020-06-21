package com.example.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.annotation.Autowired

@MongoSpringBootTest
open class UserRepository1Test {

    @Autowired
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setup() {
        userRepository.deleteAll().block()
    }

    @Test
    fun `gets by email`() {
        userRepository.save(User("login", "login@mail.com")).block()

        assertThat(userRepository.findByEmail("login@mail.com").block()).isNotNull
    }

    @Test
    fun `gets by login`() {
        userRepository.save(User("login", "login@mail.com")).block()

        assertThat(userRepository.findByLogin("login").block()).isNotNull
    }

    @Test
    fun `return many users`() {
        (1..50).forEach {
            userRepository.save(User("login$it", "login$it@mail.com")).block()
        }

        assertThat(userRepository.findAll().collectList().block()).hasSize(50)
    }

    @TestFactory
    fun repeat(): Collection<DynamicTest> {
        return (1..4).map {
            DynamicTest.dynamicTest("repeat $it") {
                setup()
                userRepository.save(User("login", "login@mail.com")).block()
                Thread.sleep(100)
                assertThat(userRepository.findAll().collectList().block()).hasSize(1)
            }
        }
    }
}
