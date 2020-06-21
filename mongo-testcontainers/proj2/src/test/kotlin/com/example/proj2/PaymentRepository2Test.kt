package com.example.proj2

import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("resetContext")
class PaymentRepository2Test : PaymentRepository1Test() {
}
