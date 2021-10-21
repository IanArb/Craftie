package com.craftie.android.di

import com.craftie.data.repository.*
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
    fun provideFavouritesRepository(): FavouritesRepository {
        return FavouritesRepository()
    }

    @Provides
    fun provideRecentSearchesRepository(): RecentSearchesRepository {
        return RecentSearchesRepository()
    }

    @Provides
    fun provideBeerRatingsRepository(): CraftieBeerRatingsRepository {
        return CraftieBeerRatingsRepository()
    }

}