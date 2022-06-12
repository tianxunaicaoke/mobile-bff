package com.tian.darkhorse.domain

import com.tian.darkhorse.TestCoroutineRule
import com.tian.darkhorse.data.Repository
import com.tian.darkhorse.data.entity.FlightItinerary
import com.tian.darkhorse.data.entity.PaymentResult
import com.tian.darkhorse.data.network.Result
import com.tian.darkhorse.presentation.model.Itinerary
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

internal class GetItineraryUseCaseTest {

    private val repository: Repository = mockk()
    private val getItineraryUseCase: GetItineraryUseCase = GetItineraryUseCase(repository)

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @Test
    fun `should get the payment result when invoke ticketPaymentUseCase success`() =
        coroutineTestRule.runBlockingTest {
            // GIVEN
            coEvery { repository.getRecentFlightsItineraries() } answers {
                flowOf(Result.success(generateFlightsItineraries()))
            }
            // WHEN
            val result = getItineraryUseCase()
            // THEN
            result.collect { result ->
                generateFlightsItineraries().forEachIndexed { index, it ->
                    Assert.assertEquals(it.price.toString(), result.getOrNull()?.get(index)?.price)
                    Assert.assertEquals(it.ticketId, result.getOrNull()?.get(index)?.ticketId)
                    Assert.assertEquals(it.ete, result.getOrNull()?.get(index)?.ete)
                    Assert.assertEquals(it.time.toString(), result.getOrNull()?.get(index)?.time)
                    Assert.assertEquals(it.from, result.getOrNull()?.get(index)?.from)
                    Assert.assertEquals(it.to, result.getOrNull()?.get(index)?.to)
                }
            }
        }

    @Test
    fun `should get the error when invoke ticketPaymentUseCase failure`() =
        coroutineTestRule.runBlockingTest {
            // GIVEN
            coEvery { repository.getRecentFlightsItineraries() } answers {
                flowOf(Result.failure(RuntimeException("test")))
            }
            // WHEN
            val result = getItineraryUseCase()
            // THEN
            result.collect {
                Assert.assertEquals(
                    "test",
                    it.exceptionOrNull()?.message
                )
            }
        }

    private fun generateFlightsItineraries(): List<FlightItinerary> {
        return listOf(
            FlightItinerary(
                "test",
                "test",
                1,
                "test",
                "test",
                "test",
                800.0,
            )
        )
    }
}