package com.tian.darkhorse.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class FlightItinerary(
    val ticketId: String,
    val airline: String,
    val time: Long,
    val ete: String,
    val from: String,
    val to: String,
    val price: Double
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}

data class PaymentResult(
    val code: Int,
    @SerializedName("payment-result")
    val message: String
)
