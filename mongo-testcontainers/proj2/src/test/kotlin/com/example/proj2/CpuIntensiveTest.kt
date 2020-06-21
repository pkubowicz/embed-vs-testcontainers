package com.example.proj2

import org.junit.jupiter.api.Test
import java.security.MessageDigest
import java.util.*

class CpuIntensiveTest {

    @Test
    fun `runs CPU-intensive computation and shifts proj2 execution in comparison to proj1`() {
        (1..1000).forEach {
            MessageDigest.getInstance("SHA-512").apply {
                update(UUID.randomUUID().toString().toByteArray())
                update(it.toByte())
            }.digest().let { println(it) }
        }
    }
}
