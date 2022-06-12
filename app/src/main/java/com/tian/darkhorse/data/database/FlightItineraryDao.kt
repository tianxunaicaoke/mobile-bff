package com.tian.darkhorse.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tian.darkhorse.data.entity.FlightItinerary
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightItineraryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFlightItineraries( flightItineraries: List<FlightItinerary>)
    @Query("SELECT * FROM flightItinerary")
    fun getAllFlightItineraries(): List<FlightItinerary>
}