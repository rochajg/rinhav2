package dev.rochajg.domain.usecase.transaction

import dev.rochajg.domain.entity.transaction.Transaction
import dev.rochajg.domain.gateway.transaction.TransactionGateway
import dev.rochajg.domain.gateway.user.UserGateway
import dev.rochajg.domain.usecase.exception.UserNotFoundException
import dev.rochajg.domain.usecase.transaction.entity.CreatedTransaction
import dev.rochajg.domain.usecase.transaction.entity.TransactionRequest
import dev.rochajg.domain.usecase.transaction.exception.InsufficientBalanceException
import dev.rochajg.domain.usecase.transaction.exception.InvalidTransactionRequestException
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

        if (kotlin.math.abs(transactionRequest.value - user.balance) > user.limit) {
            throw InsufficientBalanceException(user.id, user.balance)
        }

        val newBalance =
            when (transactionRequest.type) {
                "c" -> user.balance + transactionRequest.value
                "d" -> user.balance - transactionRequest.value
                else -> throw InvalidTransactionRequestException("Invalid transaction type")
            }

        transactionGateway.create(
            Transaction(
                userId = user.id,
                value = transactionRequest.value,
                operation = transactionRequest.type,
                description = transactionRequest.description,
            ),
        )

        return user.copy(balance = newBalance)
            .apply(userGateway::update)
            .let { CreatedTransaction(limite = it.limit, saldo = it.balance) }
    }
}
