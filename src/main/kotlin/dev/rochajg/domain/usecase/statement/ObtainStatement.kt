package dev.rochajg.domain.usecase.statement

import dev.rochajg.domain.entity.user.UserBalance
import dev.rochajg.domain.entity.user.UserTransaction
import dev.rochajg.domain.entity.user.UserTransactions
import dev.rochajg.domain.gateway.user.UserGateway
import dev.rochajg.domain.usecase.exception.UserNotFoundException
import jakarta.inject.Singleton
import java.util.Date

@Singleton
class ObtainStatement(
    private val userGateway: UserGateway,
) {
    fun getStatement(userId: Int): UserTransaction {
        val user = userGateway.get(userId) ?: throw UserNotFoundException(userId)

        return UserTransaction(
            saldo =
                UserBalance(
                    total = user.balance,
                    dataExtrato = Date(),
                    limite = user.limit,
                ),
            ultimasTransacoes =
                user.transactions
                    .map {
                        UserTransactions(
                            valor = it.value,
                            tipo = it.operation,
                            descricao = it.description,
                            realizadaEm = it.createdAt,
                        )
                    },
        )
    }
}
