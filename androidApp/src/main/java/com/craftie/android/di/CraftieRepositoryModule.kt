package com.craftie.android.di

import com.craftie.data.repository.CraftieBeersRepository
import com.craftie.data.repository.CraftieBreweriesRepository
import com.craftie.data.repository.CraftieProvincesRepository
import com.craftie.data.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CraftieRepositoryModule {

    @Provides
    fun provideCraftieBreweriesRepository(): CraftieBreweriesRepository {
        return CraftieBreweriesRepository()
    }

    @Provides
    fun provideCraftieBeersRepository(): CraftieBeersRepository {
        return CraftieBeersRepository()
    }

    @Provides
    fun provideCraftieProvincesRepository(): CraftieProvincesRepository {
        return CraftieProvincesRepository()
    }

    @Provides
    fun provideDatabaseRepository(): DatabaseRepository {
        return DatabaseRepository()
    }

}