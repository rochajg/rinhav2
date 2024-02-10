package dev.rochajg.domain.usecase.transaction

import dev.rochajg.domain.entity.transaction.Transaction
import dev.rochajg.domain.gateway.transaction.TransactionGateway
import dev.rochajg.domain.gateway.user.UserGateway
import dev.rochajg.domain.usecase.transaction.entity.CreatedTransaction
import dev.rochajg.domain.usecase.transaction.entity.TransactionRequest
import dev.rochajg.domain.usecase.transaction.exception.InsufficientBalanceException
import dev.rochajg.domain.usecase.transaction.exception.UserNotFoundException
import jakarta.inject.Singleton

@Singleton
class CreateTransactionUseCase(
    private val userGateway: UserGateway,
    private val transactionGateway: TransactionGateway,
) {
    fun createTransaction(
        userId: Int,
        transactionRequest: TransactionRequest,
    ): CreatedTransaction {
        val user = userGateway.get(userId) ?: throw UserNotFoundException(userId)
        var newBalance = user.balance

        if (transactionRequest.type == "c") {
            newBalance += transactionRequest.value
        } else {
            if (user.balance - transactionRequest.value < -user.limit) {
                throw InsufficientBalanceException(userId)
            }

            newBalance -= transactionRequest.value
        }

        transactionGateway.create(
            Transaction(
                userId = userId,
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
