package com.craftie.android.presentation.discovery

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.utils.CoroutineTestRule
import com.craftie.android.utils.StubData
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
class DiscoveryViewModelTest {

    private val discoveryUseCase: DiscoveryUseCase = mockk()

    private lateinit var discoveryViewModel: DiscoveryViewModel

    @get:Rule
    var coroutineRule = CoroutineTestRule()

    @Before
    fun setup() {
        discoveryViewModel = DiscoveryViewModel(coroutineRule.testDispatcherProvider, discoveryUseCase)
    }

    @Test
    fun `test ui data returns success`() = runTest {
        val uiState = DiscoveryUiState.Success(stubDiscoveryUiData())
        coEvery { discoveryUseCase.build() } returns flowOf(uiState)

        discoveryViewModel.init()

        discoveryViewModel.uiState.test {
            withTimeout(1000) {
                awaitEvent() shouldBe Event.Item(uiState)
            }
        }
    }

    @Test
    fun `test ui data returns failure`() = runTest {
        val uiState = DiscoveryUiState.Error
        coEvery { discoveryUseCase.build() } returns flowOf(uiState)

        discoveryViewModel.init()

        discoveryViewModel.uiState.test {
            withTimeout(1000) {
                awaitEvent() shouldBe Event.Item(uiState)
            }
        }
    }

    @Test
    fun `test ui data returns loading`() = runTest {
        discoveryViewModel.init()

        discoveryViewModel.uiState.test(timeout = Duration.seconds(5000)) {
            val loading = DiscoveryUiState.Loading
            awaitEvent() shouldBe Event.Item(loading)
        }
    }

    private fun stubDiscoveryUiData(): DiscoveryUiData = DiscoveryUiData(
        StubData.breweries().results,
        StubData.beers().results,
        StubData.provinces(),
        StubData.featuredBeer(),
    )
}