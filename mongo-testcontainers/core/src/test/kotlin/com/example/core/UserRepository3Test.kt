package com.example.core

import org.springframework.boot.test.mock.mockito.MockBean
import java.time.Clock

class UserRepository3Test : UserRepository1Test() {
    @MockBean
    private lateinit var clock: Clock
}
