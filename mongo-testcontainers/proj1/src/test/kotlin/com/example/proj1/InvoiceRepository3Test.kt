package com.example.proj1

import org.springframework.boot.test.mock.mockito.MockBean
import java.time.Clock

class InvoiceRepository3Test : InvoiceRepository1Test() {
    @MockBean
    private lateinit var clock: Clock
}
