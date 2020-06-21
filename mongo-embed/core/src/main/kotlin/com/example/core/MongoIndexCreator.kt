package com.example.core

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.index.Index
import org.springframework.stereotype.Component

@Component
internal class MongoIndexCreator(private val mongoTemplate: MongoTemplate) {

    @EventListener(ApplicationReadyEvent::class)
    fun initIndicesAfterStartup() {
        mongoTemplate.indexOps("user").ensureIndex(Index().on("email", Sort.Direction.ASC).unique())
    }
}
