package com.craftie.android.presentation.viewAllbreweries

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.presentation.viewAllBreweries.ViewAllBreweriesUiState
import com.craftie.android.presentation.viewAllBreweries.ViewAllBreweriesUseCase
import com.craftie.android.utils.StubData
import com.craftie.data.repository.CraftieBreweriesRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.time.ExperimentalTime

@ExperimentalTime
class ViewAllBreweriesUseCaseTest {

    private val repository: CraftieBreweriesRepository = mockk()

    private lateinit var useCase: ViewAllBreweriesUseCase

    @Before
    fun setup() {
        useCase = ViewAllBreweriesUseCase(repository)
    }

    @Test
    fun `test that breweries returns success`() {
        runBlockingTest {
            val breweries = StubData.breweries()
            coEvery { repository.breweries() } returns breweries

            useCase.breweries().test {
                val uiState = ViewAllBreweriesUiState.Success(breweries)
                expectEvent() shouldBe Event.Item(uiState)
                expectComplete()
            }
        }
    }

    @Test
    fun `test that breweries returns error`() {
        runBlockingTest {
            val breweries = StubData.breweries()
            coEvery { repository.breweries() } throws IOException()

            useCase.breweries().test {
                val uiState = ViewAllBreweriesUiState.Error
                expectEvent() shouldBe Event.Item(uiState)
                expectComplete()
            }
        }
    }


}