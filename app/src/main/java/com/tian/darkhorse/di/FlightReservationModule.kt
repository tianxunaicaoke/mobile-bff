package com.tian.darkhorse.di

import android.content.Context
import androidx.room.Room
import com.tian.darkhorse.data.CatchableDataSource
import com.tian.darkhorse.data.DataSource
import com.tian.darkhorse.data.FlightItineraryRepository
import com.tian.darkhorse.data.LocalDataSource
import com.tian.darkhorse.data.RemoteDataSource
import com.tian.darkhorse.data.Repository
import com.tian.darkhorse.data.database.FlightItineraryDataBase
import com.tian.darkhorse.data.network.BffAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object FlightReservationModule {
    @Provides
    @Singleton
    fun provideRepository(
       localDataSource: CatchableDataSource,
       remoteDataSource: DataSource
    ): Repository {
        return FlightItineraryRepository(localDataSource, remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(bffAPI:BffAPI): DataSource {
        return RemoteDataSource(bffAPI)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(flightItineraryDataBase: FlightItineraryDataBase): CatchableDataSource {
        return LocalDataSource(flightItineraryDataBase)
    }

    @Provides
    @Singleton
    fun provideBffAPI(): BffAPI {
        return Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/tianxunaicaoke/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BffAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideLocalDB(@ApplicationContext context: Context): FlightItineraryDataBase {
        return Room.databaseBuilder(
            context,
            FlightItineraryDataBase::class.java, "itinerary-db"
        ).build()
    }
}