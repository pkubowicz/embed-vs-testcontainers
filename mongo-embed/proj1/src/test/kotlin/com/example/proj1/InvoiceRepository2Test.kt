package com.example.proj1

import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("resetContext")
class InvoiceRepository2Test : InvoiceRepository1Test() {
}
