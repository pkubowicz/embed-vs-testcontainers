package com.example.core

import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("againReloadContext")
@MongoSpringBootTest
class UserControllerTest(private val userRepository: UserRepository) : AbstractControllerTest() {

    @BeforeEach
    fun setup() {
        userRepository.deleteAll().block()
    }

    @Test
    fun `gets by email`() {
        userRepository.save(User("login", "login@mail.com")).block()

        performAsync(
            get("/user")
        ).andExpect(status().isOk)
    }

    @Test
    fun `gets by login`() {
        userRepository.save(User("login", "login@mail.com")).block()

        performAsync(
            get("/user")
        ).andExpect(status().isOk)
    }

    @Test
    fun `return many users`() {
        (1..50).forEach {
            userRepository.save(User("login$it", "login$it@mail.com")).block()
        }

        performAsync(
            get("/user")
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(50)))
    }

    @TestFactory
    fun repeat(): Collection<DynamicTest> {
        return (1..20).map {
            dynamicTest("repeat $it") {
                setup()
                Thread.sleep(100)
                performAsync(
                    get("/user")
                ).andExpect(status().isOk)
            }
        }
    }
}
