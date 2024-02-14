package dev.rochajg.domain.usecase.transaction

import dev.rochajg.domain.entity.transaction.Transaction
import dev.rochajg.domain.gateway.transaction.TransactionGateway
import dev.rochajg.domain.gateway.user.UserGateway
import dev.rochajg.domain.usecase.exception.UserNotFoundException
import dev.rochajg.domain.usecase.transaction.entity.CreatedTransaction
import dev.rochajg.domain.usecase.transaction.entity.TransactionRequest
import dev.rochajg.domain.usecase.transaction.entity.TransactionType
import dev.rochajg.domain.usecase.transaction.exception.InsufficientBalanceException
import jakarta.inject.Singleton

@Singleton
class CreateTransactionUseCase(
    private val userGateway: UserGateway,
    private val transactionGateway: TransactionGateway,
) {
    fun createTransaction(
        userId: Int?,
        transactionRequest: TransactionRequest,
    ): CreatedTransaction {
        val user = userGateway.get(userId) ?: throw UserNotFoundException(userId)
        val newBalance =
            when (transactionRequest.type) {
                TransactionType.C -> user.balance + transactionRequest.value
                TransactionType.D -> user.balance - transactionRequest.value
            }
        val newUserBalance = user.copy(balance = newBalance)

        if (kotlin.math.abs(newBalance) > user.limit) {
            throw InsufficientBalanceException(user.id, user.balance)
        }

        userGateway.update(newUserBalance)
        transactionGateway.create(
            Transaction(
                userId = user.id,
                value = transactionRequest.value,
                operation = transactionRequest.type.value(),
                description = transactionRequest.description,
            ),
        )

        return CreatedTransaction(
            limite = newUserBalance.limit,
            saldo = newUserBalance.balance,
        )
    }
}
