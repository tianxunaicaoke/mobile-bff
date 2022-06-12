package com.tian.darkhorse.data

import com.tian.darkhorse.data.entity.FlightItinerary
import com.tian.darkhorse.data.entity.PaymentResult
import com.tian.darkhorse.data.network.Result
import kotlinx.coroutines.flow.Flow


interface DataSource {
    suspend fun fetchFlightsItineraries(): Flow<Result<List<FlightItinerary>>>
    suspend fun requestTicketPayment(id: String): Flow<Result<PaymentResult>>
}

interface CatchableDataSource {
     fun saveFlightsItineraries(list: List<FlightItinerary>)
     fun getFlightsItineraries(): List<FlightItinerary>
}