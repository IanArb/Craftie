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
        runBlockingTest {
            val featuredBeer = StubData.featuredBeer()
            coEvery { craftieBeersRepository.featuredBeer() } returns featuredBeer

            featuredBeerUseCase.featuredBeer().test {
                val uiState = FeaturedBeerUiState.Success(featuredBeer)
                expectEvent() shouldBe Event.Item(uiState)
                expectComplete()
            }
        }
    }

    @Test
    fun `test that featured beer returns error`() {
        runBlockingTest {
            coEvery { craftieBeersRepository.featuredBeer() } throws IOException()

            featuredBeerUseCase.featuredBeer().test {
                val uiState = FeaturedBeerUiState.Error
                expectEvent() shouldBe Event.Item(uiState)
                expectComplete()
            }
        }
    }
}