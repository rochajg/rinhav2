package dev.rochajg.infrastructure.db.repository.transaction

import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import com.mongodb.client.result.InsertOneResult
import dev.rochajg.infrastructure.db.DatabaseNames
import dev.rochajg.infrastructure.db.schema.transaction.TransactionSchema
import jakarta.inject.Singleton

@Singleton
class TransactionRepository(
    private val mongoClient: MongoClient,
) {
    fun create(transaction: TransactionSchema): InsertOneResult =
        getCollection()
            .insertOne(transaction)

    fun getByUser(userId: Int): List<TransactionSchema> =
        getCollection()
            .find(
                Filters.eq("userId", userId),
            )
            .sort(Sorts.descending("createdAt"))
            .limit(10)
            .toList()

    private fun getCollection() =
        mongoClient
            .getDatabase(DatabaseNames.getDatabaseName())
            .getCollection(DatabaseNames.getTransactionCollectionName(), TransactionSchema::class.java)
}
