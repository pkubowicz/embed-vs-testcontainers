package com.example.proj2

import com.example.core.User
import com.example.core.UserRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
open class PaymentRepository1Test {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var paymentRepository: PaymentRepository

    @Autowired
    private lateinit var paymentService: PaymentService

    @BeforeEach
    fun setup() {
        userRepository.deleteAll().block()
        paymentRepository.deleteAll().block()
    }

    @Test
    fun `gets by id`() {
        userRepository.save(User("login", "login@mail.com")).block()
        paymentRepository.save(Payment("inv1", 100)).block()

        Assertions.assertThat(paymentRepository.findById("inv1").block()).isNotNull
    }

    @Test
    fun `returns many`() {
        userRepository.save(User("login", "login@mail.com")).block()
        (1..50).forEach {
            paymentService.addForLogin("login", "pay$it").block()
        }

        Assertions.assertThat(paymentRepository.findAll().collectList().block()).hasSize(50)
    }

    @TestFactory
    fun repeat(): Collection<DynamicTest> {
        return (1..4).map {
            DynamicTest.dynamicTest("repeat $it") {
                setup()
                userRepository.save(User("login", "payment1@mail.com")).block()
                paymentService.addForLogin("login").block()
                Thread.sleep(100)
                Assertions.assertThat(paymentRepository.findAll().blockFirst()!!.sum).isEqualTo("payment1@mail.com".length)
            }
        }
    }

}

