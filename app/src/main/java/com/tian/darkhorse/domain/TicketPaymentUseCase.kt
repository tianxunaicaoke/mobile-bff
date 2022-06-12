package com.tian.darkhorse.domain

import com.tian.darkhorse.data.Repository
import com.tian.darkhorse.data.entity.PaymentResult
import com.tian.darkhorse.data.network.Result
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class TicketPaymentUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(id: String): Flow<Result<PaymentResult>> {
        return repository.requestTicketPayment(id)
    }
}