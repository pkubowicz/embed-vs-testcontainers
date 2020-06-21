package com.example.core

import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("resetContext")
class UserRepository2Test : UserRepository1Test() {
}
