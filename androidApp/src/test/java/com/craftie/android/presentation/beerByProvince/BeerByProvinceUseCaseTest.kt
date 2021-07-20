package com.craftie.android.presentation.beerByProvince

import app.cash.turbine.Event
import app.cash.turbine.test
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
class BeerByProvinceUseCaseTest {

    private val repository: CraftieBeersRepository = mockk()

    lateinit var useCase: BeersByProvinceUseCase

    @Before
    fun setup() {
        useCase = BeersByProvinceUseCase(repository)
    }

    @Test
    fun `test provinces by Leinster province returns success`() {
        val beers = StubData.beers()
        runBlockingTest {
            coEvery { repository.findBeersByProvince("Leinster") } returns beers

            useCase.beersByProvince("Leinster").test {
                expectEvent() shouldBe Event.Item(BeersByProvinceUiState.Success(beers))
                expectComplete()
            }
        }
    }

    @Test
    fun `test provinces by Leinster province returns error`() {
        runBlockingTest {
            coEvery { repository.findBeersByProvince("Leinster") } throws IOException()

            useCase.beersByProvince("Leinster").test {
                expectEvent() shouldBe Event.Item(BeersByProvinceUiState.Error)
                expectComplete()
            }
        }
    }

    @Test
    fun `test provinces by Leinster province returns empty`() {
        runBlockingTest {
            coEvery { repository.findBeersByProvince("Ulster") } returns emptyList()

            useCase.beersByProvince("Ulster").test {
                expectEvent() shouldBe Event.Item(BeersByProvinceUiState.Empty)
                expectComplete()
            }
        }
    }
}