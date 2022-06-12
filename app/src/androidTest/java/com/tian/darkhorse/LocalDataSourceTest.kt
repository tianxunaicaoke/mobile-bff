package com.tian.darkhorse

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tian.darkhorse.data.LocalDataSource
import com.tian.darkhorse.data.database.FlightItineraryDao
import com.tian.darkhorse.data.database.FlightItineraryDataBase
import com.tian.darkhorse.data.entity.FlightItinerary
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class LocalDataSourceTest {
    private lateinit var flightItineraryDao: FlightItineraryDao
    private lateinit var db: FlightItineraryDataBase
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, FlightItineraryDataBase::class.java
        ).build()
        flightItineraryDao = db.flightItineraryDao()
        localDataSource = LocalDataSource(db)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        localDataSource.saveFlightsItineraries(generateFlightsItineraries())
        val list = localDataSource.getFlightsItineraries()
        assertThat(generateFlightsItineraries(), equalTo(list))
    }

    private fun generateFlightsItineraries(): List<FlightItinerary> {
        return listOf(
            FlightItinerary(
                "test",
                "test",
                1,
                "test",
                "test",
                "test",
                800.0,
            )
        )
    }
}