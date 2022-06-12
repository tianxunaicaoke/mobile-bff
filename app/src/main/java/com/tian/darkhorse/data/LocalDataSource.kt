package com.tian.darkhorse.data

import com.tian.darkhorse.data.database.FlightItineraryDataBase
import com.tian.darkhorse.data.entity.FlightItinerary
import com.tian.darkhorse.data.entity.PaymentResult
import com.tian.darkhorse.data.network.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class LocalDataSource(
    private val flightItineraryDataBase: FlightItineraryDataBase
    ) : DataSource, CatchableDataSource {
    override suspend fun fetchFlightsItineraries(): Flow<Result<List<FlightItinerary>>> {
        throw IllegalAccessException()
    }

    override suspend fun requestTicketPayment(id: String): Flow<Result<PaymentResult>> {
        throw IllegalAccessException()
    }
    override fun saveFlightsItineraries(list: List<FlightItinerary>) {
        flightItineraryDataBase.flightItineraryDao()
            .insertAllFlightItineraries(list)
    }

    override fun getFlightsItineraries(): List<FlightItinerary> =
      flightItineraryDataBase.flightItineraryDao().getAllFlightItineraries()
}