package com.tian.darkhorse.data

import com.tian.darkhorse.TestCoroutineRule
import com.tian.darkhorse.data.entity.FlightItinerary
import com.tian.darkhorse.data.entity.PaymentResult
import com.tian.darkhorse.data.network.BffAPI
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RemoteDataSourceTest {
    private val bffAPI: BffAPI = mockk()
    private val remoteDataSource = RemoteDataSource(bffAPI)

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @Test
    fun `should get the FlightsItineraries when invoke the bff fetchFlightsItineraries success`() =
        coroutineTestRule.runBlockingTest {
            // GIVEN
            coEvery { bffAPI.fetchFlightsItineraries() } answers {
                generateFlightsItineraries()
            }
            // WHEN
            val result = remoteDataSource.fetchFlightsItineraries()
            // THEN
            result.collect {
                assertEquals(generateFlightsItineraries(), it.getOrNull())
            }
        }

    @Test
    fun `should get the PaymentResult when invoke the bff requestPrivatePayment success`() =
        coroutineTestRule.runBlockingTest {
            // GIVEN
            coEvery { bffAPI.requestPrivatePayment(any()) } answers {
                PaymentResult(1, "test")
            }
            // WHEN
            val result = remoteDataSource.requestTicketPayment("123")
            // THEN
            result.collect {
                assertEquals(PaymentResult(1, "test"), it.getOrNull())
            }
        }

    @Test
    fun `should get the error when invoke bff fetchFlightsItineraries failure`() =
        coroutineTestRule.runBlockingTest {
            // GIVEN
            coEvery { bffAPI.fetchFlightsItineraries() } answers {
                throw RuntimeException("test")
            }
            // WHEN
            val result = remoteDataSource.fetchFlightsItineraries()
            // THEN
            result.collect {
                assertEquals(emptyList<FlightItinerary>(), it.value)
            }
        }

    @Test
    fun `should get the error when invoke bff requestPrivatePayment failure`() =
        coroutineTestRule.runBlockingTest {
            // GIVEN
            coEvery { bffAPI.requestPrivatePayment(any()) } answers {
                throw RuntimeException("test")
            }
            // WHEN
            val result = remoteDataSource.requestTicketPayment("123")
            // THEN
            result.collect {
                assertEquals(PaymentResult(0, ""), it.value)
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