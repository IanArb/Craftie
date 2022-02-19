package com.craftie.android.presentation.featuredBeer

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.presentation.featuredBeer.FeaturedBeerUiState
import com.craftie.android.presentation.featuredBeer.FeaturedBeerUseCase
import com.craftie.android.utils.StubData
import com.craftie.data.repository.CraftieBeersRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.time.ExperimentalTime

@ExperimentalTime
class FeaturedBeerUseCaseTest {

    private val craftieBeersRepository: CraftieBeersRepository = mockk()

    lateinit var featuredBeerUseCase: FeaturedBeerUseCase

    @Before
    fun setup() {
        featuredBeerUseCase = FeaturedBeerUseCase(craftieBeersRepository)
    }

    @Test
    fun `test that featured beer returns success`() {
        runTest {
            val featuredBeer = StubData.featuredBeer()
            coEvery { craftieBeersRepository.featuredBeer() } returns featuredBeer

            featuredBeerUseCase.featuredBeer().test {
                val uiState = FeaturedBeerUiState.Success(featuredBeer)
                awaitEvent() shouldBe Event.Item(uiState)
                awaitComplete()
            }
        }
    }

    @Test
    fun `test that featured beer returns error`() {
        runTest {
            coEvery { craftieBeersRepository.featuredBeer() } throws IOException()

            featuredBeerUseCase.featuredBeer().test {
                val uiState = FeaturedBeerUiState.Error
                awaitEvent() shouldBe Event.Item(uiState)
                awaitComplete()
            }
        }
    }
}