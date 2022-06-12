package com.tian.darkhorse.data

import androidx.compose.ui.Modifier.Companion.any
import com.tian.darkhorse.TestCoroutineRule
import com.tian.darkhorse.data.entity.FlightItinerary
import com.tian.darkhorse.data.entity.PaymentResult
import com.tian.darkhorse.data.network.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.lang.RuntimeException
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FlightItineraryRepositoryTest {
    private val localDataSource: CatchableDataSource = mockk()
    private val remoteDataSource: DataSource = mockk()
    private val flightItineraryRepository: FlightItineraryRepository =
        FlightItineraryRepository(localDataSource, remoteDataSource)

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @Before
    fun setUp() {
        coEvery { localDataSource.saveFlightsItineraries(any()) } answers { {} }
        coEvery { localDataSource.getFlightsItineraries() } answers { emptyList() }
    }

    @Test
    fun `should get 3 item when 3 flightItinerary in 30 days 2 out 30 days`() =
        coroutineTestRule.runBlockingTest {
            // GIVEN
            coEvery { remoteDataSource.fetchFlightsItineraries() } answers {
                flowOf(Result.success(generateFlightItineraries()))
            }
            // WHEN
            val result = flightItineraryRepository.getRecentFlightsItineraries()
            // THEN
            result.collect {
                assertEquals(3, it.getOrNull()?.size)
            }
        }

    @Test
    fun `should get 0 item when all flightItinerary are out 30 days`() =
        coroutineTestRule.runBlockingTest {
            // GIVEN
            coEvery { remoteDataSource.fetchFlightsItineraries() } answers {
                flowOf(Result.success(generateFlightItineraries1()))
            }
            // WHEN
            val result = flightItineraryRepository.getRecentFlightsItineraries()
            // THEN
            result.collect {
                assertEquals(0, it.getOrNull()?.size)
            }
        }

    @Test
    fun `should get flightItinerary from localDataSource when get 0 from remoteDataSource`() =
        coroutineTestRule.runBlockingTest {
            // GIVEN
            coEvery { remoteDataSource.fetchFlightsItineraries() } answers {
                flowOf(Result.success(emptyList()))
            }
            // WHEN
            flightItineraryRepository.getRecentFlightsItineraries().collect()
            // THEN
            coVerify {
                localDataSource.getFlightsItineraries()
            }
        }

    @Test
    fun `should get success payment result when invoke requestTicketPayment success`() =
        coroutineTestRule.runBlockingTest {
            // GIVEN
            coEvery { remoteDataSource.requestTicketPayment(any()) } answers {
                flowOf(Result.success(PaymentResult(200, "success")))
            }
            // WHEN
            val result = flightItineraryRepository.requestTicketPayment("123")

            // THEN
            result.collect {
                assertEquals(200, it.getOrNull()?.code)
                assertEquals("success", it.getOrNull()?.message)
            }
        }

    @Test
    fun `should get error payment result when invoke requestTicketPayment failure`() =
        coroutineTestRule.runBlockingTest {
            // GIVEN
            coEvery { remoteDataSource.requestTicketPayment(any()) } answers {
                flowOf(Result.failure(RuntimeException("failure")))
            }
            // WHEN
            val result = flightItineraryRepository.requestTicketPayment("123")

            // THEN
            result.collect {
                assertEquals("failure", it.exceptionOrNull()?.message)
            }
        }

    private fun generateFlightItineraries() =
        listOf(
            FlightItinerary("", "", 1654877729940, "", "", "", 0.0),
            FlightItinerary("", "", 1654877729940, "", "", "", 0.0),
            FlightItinerary("", "", 1654877729940, "", "", "", 0.0),
            FlightItinerary("", "", 1654877719920, "", "", "", 0.0),
            FlightItinerary("", "", 1654877719920, "", "", "", 0.0)
        )

    private fun generateFlightItineraries1() =
        listOf(
            FlightItinerary("", "", 1654877719920, "", "", "", 0.0),
            FlightItinerary("", "", 1654877719920, "", "", "", 0.0),
            FlightItinerary("", "", 1654877719920, "", "", "", 0.0),
            FlightItinerary("", "", 1654877719920, "", "", "", 0.0),
            FlightItinerary("", "", 1654877719920, "", "", "", 0.0)
        )
}