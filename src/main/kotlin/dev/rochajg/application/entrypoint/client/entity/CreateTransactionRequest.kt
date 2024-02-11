package dev.rochajg.application.entrypoint.client.entity

import dev.rochajg.domain.usecase.transaction.entity.TransactionRequest
import dev.rochajg.domain.usecase.transaction.entity.TransactionType
import dev.rochajg.domain.usecase.transaction.exception.InvalidTransactionRequestException

data class CreateTransactionRequest(
    val valor: String,
    val tipo: String,
    val descricao: String?,
)

fun CreateTransactionRequest.toDomain(): TransactionRequest {
    when {
        this.valor.toIntOrNull() == null ->
            throw InvalidTransactionRequestException("Value must be an integer")
        this.valor.isBlank() ->
            throw InvalidTransactionRequestException("Value must be greater than zero")
        this.descricao.isNullOrBlank() ->
            throw InvalidTransactionRequestException("Description must not be blank")
        this.descricao.length !in 0..10 ->
            throw InvalidTransactionRequestException("Description must be between 0 and 10 characters")
        this.tipo !in listOf("c", "d") ->
            throw InvalidTransactionRequestException("Invalid transaction type")
    }

    return TransactionRequest(
        value = valor.toInt(),
        type = TransactionType.valueOf(tipo.uppercase()),
        description = descricao!!,
    )
}
