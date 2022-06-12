package com.tian.darkhorse.presentation.viewmodel

import com.tian.darkhorse.TestCoroutineRule
import com.tian.darkhorse.data.entity.PaymentResult
import com.tian.darkhorse.data.network.Result
import com.tian.darkhorse.domain.FakeTicketDataUseCase
import com.tian.darkhorse.domain.GetItineraryUseCase
import com.tian.darkhorse.domain.TicketPaymentUseCase
import com.tian.darkhorse.presentation.viewmodel.FlightReservationViewModel.Action.RequestItinerary
import com.tian.darkhorse.presentation.viewmodel.FlightReservationViewModel.Action.RequestPay
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FlightReservationViewModelTest {
    private val getItineraryUseCase: GetItineraryUseCase = mockk()
    private val payUseCase: TicketPaymentUseCase = mockk()
    private val getTicketDataUseCase: FakeTicketDataUseCase = mockk()
    private lateinit var flightReservationViewModel: FlightReservationViewModel

    @Before
    fun setUp() {
        every { getTicketDataUseCase() } answers {
            flowOf(Result.success(emptyList()))
        }
        flightReservationViewModel =
            FlightReservationViewModel(getItineraryUseCase, payUseCase, getTicketDataUseCase)
    }

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @Test
    fun `should invoke getItineraryUseCase when view model receive RequestItinerary Action`() =
        coroutineTestRule.runBlockingTest {
            // GIVEN
            coEvery { getItineraryUseCase() } answers {
                flowOf(Result.success(emptyList()))
            }

            // WHEN
            flightReservationViewModel.action(RequestItinerary)
            // THEN
            coVerify {
                getItineraryUseCase.invoke()
            }
        }

    @Test
    fun `should invoke paymentUseCase when view model receive RequestPay Action`() =
        coroutineTestRule.runBlockingTest {
            // GIVEN
            coEvery { payUseCase(any()) } answers {
                flowOf(Result.success(PaymentResult(1, "")))
            }

            // WHEN
            flightReservationViewModel.action(RequestPay("1"))
            // THEN
            coVerify {
                payUseCase.invoke(any())
            }
        }
}