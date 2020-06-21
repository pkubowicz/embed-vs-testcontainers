package com.example.proj2

import org.springframework.boot.test.mock.mockito.MockBean
import java.time.Clock

class PaymentRepository3Test : PaymentRepository1Test() {
    @MockBean
    private lateinit var clock: Clock
}
