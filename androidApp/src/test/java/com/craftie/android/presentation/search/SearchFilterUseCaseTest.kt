package com.craftie.android.presentation.search

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
class SearchFilterUseCaseTest {

    private val beersRepository: CraftieBeersRepository = mockk()

    private lateinit var searchFilterUseCase: SearchFilterUseCase

    @Before
    fun setup() {
        searchFilterUseCase = SearchFilterUseCase(beersRepository)
    }

    @Test
    fun `test that find beer by keyword 'ale' returns success`() = runBlockingTest {
        val aleBeers = StubData.aleBeers()
        coEvery { beersRepository.findBeersByKeyword("Ale") } returns aleBeers

        searchFilterUseCase.findBeersByKeyword("Ale").test {
            expectEvent() shouldBe Event.Item(SearchFilterUiState.Success(aleBeers))
            expectComplete()
        }
    }

    @Test
    fun `test that find beer by keyword 'ale' returns error`() = runBlockingTest {
        coEvery { beersRepository.findBeersByKeyword("Ale") } throws IOException()

        searchFilterUseCase.findBeersByKeyword("Ale").test {
            expectEvent() shouldBe Event.Item(SearchFilterUiState.Error)
            expectComplete()
        }
    }

    @Test
    fun `test that find beer by empty keyword returns empty`() = runBlockingTest {
        coEvery { beersRepository.findBeersByKeyword("") } returns emptyList()

        searchFilterUseCase.findBeersByKeyword("").test {
            expectEvent() shouldBe Event.Item(SearchFilterUiState.Empty)
            expectComplete()
        }
    }
}