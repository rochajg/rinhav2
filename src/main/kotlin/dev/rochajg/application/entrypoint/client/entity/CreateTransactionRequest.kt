package dev.rochajg.application.entrypoint.client.entity

import dev.rochajg.domain.usecase.transaction.entity.TransactionRequest

data class CreateTransactionRequest(
    val valor: Int,
    val tipo: String,
    val descricao: String,
)

fun CreateTransactionRequest.toDomain() =
    TransactionRequest(
        value = valor,
        type = tipo,
        description = descricao,
    )
