package com.tian.darkhorse.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tian.darkhorse.data.entity.FlightItinerary

@Database(entities = [FlightItinerary::class], version = 1)
abstract class FlightItineraryDataBase : RoomDatabase() {
    abstract fun flightItineraryDao(): FlightItineraryDao
}