package com.tian.darkhorse.data

import com.tian.darkhorse.data.entity.FlightItinerary
import com.tian.darkhorse.data.entity.PaymentResult
import com.tian.darkhorse.data.network.BffAPI
import com.tian.darkhorse.data.network.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf

class RemoteDataSource(private val bffAPI: BffAPI) : DataSource {
    override suspend fun fetchFlightsItineraries(): Flow<Result<List<FlightItinerary>>> {
        return try{
            flowOf(Result.success(bffAPI.fetchFlightsItineraries()))
        }catch (e:Exception){
            flowOf(Result.success(emptyList()))
        }
    }

    override suspend fun requestTicketPayment(id: String): Flow<Result<PaymentResult>> {
        return try {
            flowOf(Result.success(bffAPI.requestPrivatePayment(id)))
        }catch (e:Exception){
            flowOf(Result.success(PaymentResult(0,"")))
        }
    }
}