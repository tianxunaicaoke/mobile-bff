package com.tian.darkhorse.presentation.model

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

data class Itinerary(
    val ticketId: String,
    val airline: String,
    val time: String,
    val ete: String,
    val from: String,
    val to: String,
    val price: String
)

@Parcelize
data class Ticket(
    val ticketId: String,
    val id: String,
    val airline: String,
    val startTime: String,
    val arrivalTime: String,
    val timeZone: String,
    val from: String,
    val to: String,
    val price: String,
    val hasFirstClass: Boolean,
    val hasEconomyClass: Boolean
) : Parcelable {
    companion object NavigationType : NavType<Ticket>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): Ticket? {
            return bundle.getParcelable(key)
        }

        override fun parseValue(value: String): Ticket {
            return Gson().fromJson(value, Ticket::class.java)
        }

        override fun put(bundle: Bundle, key: String, value: Ticket) {
            bundle.putParcelable(key, value)
        }
    }
}

data class AlertMessage(
    val title: String,
    val message: String
)
