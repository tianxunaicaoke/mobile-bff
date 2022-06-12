package com.tian.darkhorse.domain

import com.tian.darkhorse.TestCoroutineRule
import com.tian.darkhorse.data.Repository
import com.tian.darkhorse.data.entity.PaymentResult
import com.tian.darkhorse.data.network.Result
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TicketPaymentUseCaseTest {

    private val repository: Repository = mockk()
    private val ticketPaymentUseCase: TicketPaymentUseCase = TicketPaymentUseCase(repository)

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @Test
    fun `should get the payment result when invoke ticketPaymentUseCase success`() =
        coroutineTestRule.runBlockingTest {
            // GIVEN
            coEvery { repository.requestTicketPayment(any()) } answers {
                flowOf(Result.success(generatePaymentResult()))
            }
            // WHEN
            val result = ticketPaymentUseCase("1")
            // THEN
            result.collect {
                assertEquals(generatePaymentResult(), it.value)
            }
        }

    @Test
    fun `should get the error when invoke ticketPaymentUseCase failure`() =
        coroutineTestRule.runBlockingTest {
            // GIVEN
            coEvery { repository.requestTicketPayment(any()) } answers {
                flowOf(Result.failure(RuntimeException("test")))
            }
            // WHEN
            val result = ticketPaymentUseCase("1")
            // THEN
            result.collect {
                assertEquals(
                   "test",
                    it.exceptionOrNull()?.message
                )
            }
        }


    private fun generatePaymentResult() = PaymentResult(
        1,
        "tesxt"
    )
}