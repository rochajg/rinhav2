package dev.rochajg.domain.entity.user

import java.util.Date

data class UserTransaction(
    val saldo: UserBalance,
    val ultimasTransacoes: List<UserTransactions> = emptyList(),
)

data class UserBalance(
    val total: Int,
    val dataExtrato: Date,
    val limite: Int,
)

data class UserTransactions(
    val valor: Int,
    val tipo: String,
    val descricao: String,
    val realizadaEm: Date,
)
