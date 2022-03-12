package com.craftie.android.presentation.beerDetail

import app.cash.turbine.Event
import app.cash.turbine.test
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
class BeerDetailUseCaseTest {

    private val beersRepository: CraftieBeersRepository = mockk()

    private lateinit var beerDetailUseCase: BeerDetailUseCase

    @Before
    fun setup() {
        beerDetailUseCase = BeerDetailUseCase(beersRepository)
    }

    @Test
    fun `test that find beer by id returns success`() {
        val beer = StubData.beers().first()
        runTest {
            coEvery { beersRepository.findBeer("1") } returns beer

            beerDetailUseCase.beer("1").test {
                awaitEvent() shouldBe Event.Item(BeerDetailUiState.Success(beer))
                awaitComplete()
            }
        }
    }

    @Test
    fun `test that find beer by id returns error`() { runTest {
            coEvery { beersRepository.findBeer("1") } throws IOException()

            beerDetailUseCase.beer("1").test {
                awaitEvent() shouldBe Event.Item(BeerDetailUiState.Error)
                awaitComplete()
            }
        }
    }
}