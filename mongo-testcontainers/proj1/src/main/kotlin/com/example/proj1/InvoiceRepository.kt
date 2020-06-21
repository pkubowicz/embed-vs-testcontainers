package com.example.proj1

import com.example.core.UserRepository
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Repository
interface InvoiceRepository : ReactiveMongoRepository<Invoice, String> {
}

@Service
class InvoiceService(private val invoiceRepository: InvoiceRepository, private val userRepository: UserRepository) {
    fun addForLogin(login: String, invoiceId: String = login): Mono<Invoice> {
        return userRepository.findByLogin(login)
            .switchIfEmpty(Mono.error(IllegalStateException("no user with login $login")))
            .log()
            .flatMap { user -> invoiceRepository.save(Invoice(invoiceId, user.email.length)) }
    }
}

data class Invoice(
    val id: String,
    val sum: Int
)
