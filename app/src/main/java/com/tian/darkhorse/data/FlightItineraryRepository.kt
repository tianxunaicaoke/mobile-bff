package com.tian.darkhorse.data

import com.tian.darkhorse.data.entity.FlightItinerary
import com.tian.darkhorse.data.entity.PaymentResult
import com.tian.darkhorse.data.network.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

class FlightItineraryRepository(
    private val localDataSource: CatchableDataSource,
    private val remoteDataSource: DataSource
) : Repository {
    override suspend fun getRecentFlightsItineraries(): Flow<Result<List<FlightItinerary>>> {
        return remoteDataSource.fetchFlightsItineraries().flatMapConcat {
            when (val result = it.getOrNull()) {
                null ->
                    flowOf(transform(localDataSource.getFlightsItineraries()))
                else -> {
                    if (result.isEmpty()) {
                        flowOf(transform(localDataSource.getFlightsItineraries()))
                    } else {
                        localDataSource.saveFlightsItineraries(result)
                        flowOf(transform(result))
                    }
                }
            }
        }
    }

    override suspend fun requestTicketPayment(id: String): Flow<Result<PaymentResult>> =
        remoteDataSource.requestTicketPayment(id)

    private fun transform(list: List<FlightItinerary>): Result<List<FlightItinerary>> =
        Result.success(list.filter { flightItinerary ->
            flightItinerary.time.isRecent()
        })

    private fun Long.isRecent(): Boolean {
        val currentTime: Long = 1654880311939
        return this > currentTime || (currentTime - this < RECENT_TIME)
    }

    companion object {
        const val RECENT_TIME = 30 * 24 * 3600
    }
}