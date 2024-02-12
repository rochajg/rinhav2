package dev.rochajg.domain.usecase.transaction

import dev.rochajg.domain.entity.transaction.Transaction
import dev.rochajg.domain.gateway.user.UserGateway
import dev.rochajg.domain.usecase.exception.UserNotFoundException
import dev.rochajg.domain.usecase.transaction.entity.CreatedTransaction
import dev.rochajg.domain.usecase.transaction.entity.TransactionRequest
import dev.rochajg.domain.usecase.transaction.entity.TransactionType
import dev.rochajg.domain.usecase.transaction.exception.InsufficientBalanceException
import jakarta.inject.Singleton
import java.util.Date

@Singleton
class CreateTransactionUseCase(
    private val userGateway: UserGateway,
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

        userGateway.update(
            user.copy(
                balance = newBalance,
                transactions =
                    listOf(
                        Transaction(
                            value = transactionRequest.value,
                            operation = transactionRequest.type.value(),
                            description = transactionRequest.description,
                            createdAt = Date(),
                        ),
                    ).plus(user.transactions),
            ),
        )

        return CreatedTransaction(
            limite = newUserBalance.limit,
            saldo = newUserBalance.balance,
        )
    }
}
