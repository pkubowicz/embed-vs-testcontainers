package com.example.proj2

import com.example.core.UserRepository
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Repository
interface PaymentRepository : ReactiveMongoRepository<Payment, String> {
}

@Service
class PaymentService(private val paymentRepository: PaymentRepository, private val userRepository: UserRepository) {
    fun addForLogin(login: String, paymentId: String = login): Mono<Payment> {
        return userRepository.findByLogin(login)
            .switchIfEmpty(Mono.error(IllegalStateException("no user with login $login")))
            .log()
            .flatMap { user -> paymentRepository.save(Payment(paymentId, user.email.length)) }
    }
}

data class Payment(
    val id: String,
    val sum: Int
)
