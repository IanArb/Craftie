package com.craftie.android.presentation.viewAllTopRated

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
class ViewAllTopRatedUseCaseTest {

    private val repository: CraftieBeersRepository = mockk()

    lateinit var useCase: ViewAllTopRatedUseCase

    @Before
    fun setup() {
        useCase = ViewAllTopRatedUseCase(repository)
    }

    @Test
    fun `test that top rated beers returns success`() {
        runBlockingTest {
            val beers = StubData.beers()
            coEvery { repository.beers() } returns beers

            useCase.beers().test {
                val uiState = ViewAllTopRatedUiState.Success(beers)
                awaitEvent() shouldBe Event.Item(uiState)
                awaitComplete()
            }
        }
    }

    @Test
    fun `test that breweries returns error`() {
        runBlockingTest {
            coEvery { repository.beers() } throws IOException()

            useCase.beers().test {
                val uiState = ViewAllTopRatedUiState.Error
                awaitEvent() shouldBe Event.Item(uiState)
                awaitComplete()
            }
        }
    }
}