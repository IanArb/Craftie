package com.craftie.android.presentation.home

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.utils.StubData
import com.craftie.data.repository.ProvincesCountRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class FavouriteBeersUseCaseTest {

    private val provincesCountRepository: ProvincesCountRepository = mockk()

    private lateinit var favouriteBeersUseCase: FavouriteBeersUseCase

    @Before
    fun setup() {
        favouriteBeersUseCase = FavouriteBeersUseCase(provincesCountRepository)
    }

    @Test
    fun `test fetch favourite beers by province returns success`() = runTest {
        coEvery { provincesCountRepository.provincesCount() } returns StubData.provinceCount()

        favouriteBeersUseCase.fetchFavouriteBeersByProvince(StubData.favourites()).test {
            awaitEvent() shouldBe Event.Item(FavouriteBeersUiState.Success(StubData.favouriteBeersUiData()))
            awaitEvent() shouldBe Event.Item(FavouriteBeersUiState.Success(StubData.favouriteBeersUiData()))
            awaitEvent() shouldBe Event.Item(FavouriteBeersUiState.Success(StubData.favouriteBeersUiData()))
            awaitEvent() shouldBe Event.Item(FavouriteBeersUiState.Success(StubData.favouriteBeersUiData()))
            awaitComplete()
        }
    }

    @Test
    fun `test fetch favourite beers by province returns error`() = runTest {
        coEvery { provincesCountRepository.provincesCount() } throws IOException()

        favouriteBeersUseCase.fetchFavouriteBeersByProvince(StubData.favourites()).test {
            awaitEvent() shouldBe Event.Item(FavouriteBeersUiState.Error)
            awaitComplete()
        }
    }
}