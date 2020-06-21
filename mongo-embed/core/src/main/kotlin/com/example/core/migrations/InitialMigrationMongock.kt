package com.example.core.migrations

import com.github.cloudyrock.mongock.ChangeLog
import com.github.cloudyrock.mongock.ChangeSet
import com.mongodb.client.MongoDatabase
import org.bson.Document

@ChangeLog
class InitialMigrationMongock {
    @ChangeSet(id = "InitialMigration", author = "migrator", order = "1")
    fun doMigrate(db: MongoDatabase) {
        Thread.sleep(2000);
        db.getCollection("tokens").insertOne(Document().append("_id", "admin-token"))
    }
}
