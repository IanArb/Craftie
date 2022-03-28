package com.craftie.android.di

import com.craftie.data.useCase.CraftieFilterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CraftieUseCaseModule {

    @Provides
    fun provideCraftieFilterUseCase(): CraftieFilterUseCase = CraftieFilterUseCase()
}