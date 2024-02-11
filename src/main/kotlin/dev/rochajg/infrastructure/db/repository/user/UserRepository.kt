package dev.rochajg.infrastructure.db.repository.user

import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters
import dev.rochajg.infrastructure.db.DatabaseNames
import dev.rochajg.infrastructure.db.schema.user.UserSchema
import jakarta.inject.Singleton

@Singleton
class UserRepository(
    private val mongoClient: MongoClient,
) {
    fun get(id: Int?): UserSchema? =
        getCollection()
            .find(
                Filters.eq("userId", id),
            )
            .toList()
            .firstOrNull()

    fun update(user: UserSchema) {
        getCollection()
            .replaceOne(
                Filters.eq("userId", user.userId),
                user.copy(id = null),
            )
    }

    private fun getCollection() =
        mongoClient
            .getDatabase(DatabaseNames.getDatabaseName())
            .getCollection(DatabaseNames.getUserCollectionName(), UserSchema::class.java)
}
