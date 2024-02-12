package dev.rochajg.infrastructure.db.repository.user

import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Projections.slice
import dev.rochajg.infrastructure.db.DatabaseNames
import dev.rochajg.infrastructure.db.schema.user.UserSchema
import jakarta.inject.Singleton

@Singleton
class UserRepository(
    private val mongoClient: MongoClient,
) {
    fun get(id: Int?): UserSchema? =
        getCollection()
            .find(eq("userId", id))
            .projection(
                slice("transactions", -10),
            )
            .singleOrNull()

    fun update(user: UserSchema) {
        getCollection()
            .replaceOne(
                eq("userId", user.userId),
                user.copy(id = null),
            )
    }

    private fun getCollection() =
        mongoClient
            .getDatabase(DatabaseNames.getDatabaseName())
            .getCollection(DatabaseNames.getUserCollectionName(), UserSchema::class.java)
}
