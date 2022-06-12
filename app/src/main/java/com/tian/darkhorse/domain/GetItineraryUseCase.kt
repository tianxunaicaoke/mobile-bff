package com.tian.darkhorse.domain

import com.tian.darkhorse.data.Repository
import com.tian.darkhorse.data.entity.FlightItinerary
import com.tian.darkhorse.data.network.Result
import com.tian.darkhorse.data.network.map
import com.tian.darkhorse.presentation.model.Itinerary
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetItineraryUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(): Flow<Result<List<Itinerary>>> {
        return repository.getRecentFlightsItineraries().map {
            it.map {
                it.map { it.convert() }
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun FlightItinerary.convert() = Itinerary(
        ticketId = ticketId,
        airline = airline,
        time = time.toString(),
        ete = ete,
        from = from,
        to = to,
        price = price.toString()
    )
}