package com.example.proj1

import com.example.core.User
import com.example.core.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
open class InvoiceRepository1Test {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var invoiceRepository: InvoiceRepository

    @Autowired
    private lateinit var invoiceService: InvoiceService

    @BeforeEach
    fun setup() {
        userRepository.deleteAll().block()
        invoiceRepository.deleteAll().block()
    }

    @Test
    fun `gets by id`() {
        userRepository.save(User("login", "login@mail.com")).block()
        invoiceRepository.save(Invoice("inv1", 100)).block()

        assertThat(invoiceRepository.findById("inv1").block()).isNotNull
    }

    @Test
    fun `returns many`() {
        userRepository.save(User("login", "login@mail.com")).block()
        (1..50).forEach {
            invoiceService.addForLogin("login", "inv$it").block()
        }

        assertThat(invoiceRepository.findAll().collectList().block()).hasSize(50)
    }

    @TestFactory
    fun repeat(): Collection<DynamicTest> {
        return (1..4).map {
            DynamicTest.dynamicTest("repeat $it") {
                setup()
                userRepository.save(User("login", "invoice1@mail.com")).block()
                invoiceService.addForLogin("login").block()
                Thread.sleep(100)
                assertThat(invoiceRepository.findAll().blockFirst()!!.sum).isEqualTo("invoice1@mail.com".length)
            }
        }
    }

}
