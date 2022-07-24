package com.craftie.android.di

import com.craftie.android.util.BaseUrlResolver
import com.craftie.data.repository.*
import com.craftie.data.settings.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.FlowPreview

@Module
@InstallIn(SingletonComponent::class)
object CraftieRepositoryModule {

    @Provides
    fun provideCraftieBreweriesRepository(): CraftieBreweriesRepository {
        return CraftieBreweriesRepository()
    }

    @OptIn(FlowPreview::class)
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

    @Provides
    fun provideSettingsRepository(baseUrlResolver: BaseUrlResolver): SettingsRepository {
        return SettingsRepository(baseUrlResolver.resolveBaseUrl())
    }

    @Provides
    fun providesAuthenticationRepository(): CraftieAuthenticationRepository = CraftieAuthenticationRepository()

    @Provides
    fun providesProvincesCountRepository(): ProvincesCountRepository = ProvincesCountRepository()

}