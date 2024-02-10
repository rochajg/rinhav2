package dev.rochajg.application.entrypoint.client

import dev.rochajg.application.entrypoint.client.entity.CreateTransactionRequest
import dev.rochajg.application.entrypoint.client.entity.toDomain
import dev.rochajg.application.exception.ApiException
import dev.rochajg.domain.entity.user.UserTransaction
import dev.rochajg.domain.usecase.statement.ObtainStatement
import dev.rochajg.domain.usecase.transaction.CreateTransactionUseCase
import dev.rochajg.domain.usecase.transaction.exception.InsufficientBalanceException
import dev.rochajg.domain.usecase.transaction.exception.UserNotFoundException
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post

@Controller(value = "/clientes")
class ClientsEntrypoint(
    private val obtainStatement: ObtainStatement,
    private val createTransaction: CreateTransactionUseCase,
) {
    @Post("/{user_id}/transacoes")
    fun createTransaction(
        @PathVariable(name = "user_id") userId: Int,
        @Body request: CreateTransactionRequest,
    ) = try {
        createTransaction.createTransaction(userId, request.toDomain())
    } catch (e: UserNotFoundException) {
        throw ApiException(
            error = "user_not_found",
            message = "User with id [${e.userId}] not found",
            statusCode = 404,
            cause = e,
        )
    } catch (e: InsufficientBalanceException) {
        throw ApiException(
            error = "insufficient_balance",
            message = "User [${e.userId}] doesn't have enough balance for this transaction",
            statusCode = 422,
            cause = e,
        )
    }

    @Get("/{user_id}/extrato")
    fun getStatement(
        @PathVariable(name = "user_id") userId: Int,
    ): UserTransaction = obtainStatement.getStatement(userId)
}
