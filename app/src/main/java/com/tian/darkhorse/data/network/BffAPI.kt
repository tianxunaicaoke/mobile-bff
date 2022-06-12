package com.tian.darkhorse.data.network

import com.tian.darkhorse.data.entity.FlightItinerary
import com.tian.darkhorse.data.entity.PaymentResult
import retrofit2.http.GET
import retrofit2.http.Path

interface BffAPI {
    //这块因为fake的API是一个比较简单的框架(有很多局限性)，API应该是"mobile-bff/ticket-contracts/{cid}/itinerary-requests"
    @GET("mobile-bff/itinerary-requests")
    suspend fun fetchFlightsItineraries(): List<FlightItinerary>

    //这块因为fake的API是一个比较简单的框架(有很多局限性)只提供GET的fake，这里应该是POST, 并且API应该是"/mobile-bff/ticket-contracts/{cid}/private-payments"
    @GET("mobile-bff/private-payments/{cid}")
    suspend fun requestPrivatePayment(@Path("cid") id: String): PaymentResult
}
