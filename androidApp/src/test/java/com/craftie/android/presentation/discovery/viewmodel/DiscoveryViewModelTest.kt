package com.craftie.android.presentation.discovery.viewmodel

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.presentation.discovery.model.DiscoveryUiData
import com.craftie.android.presentation.discovery.model.DiscoveryUiState
import com.craftie.android.presentation.discovery.usecase.DiscoveryUseCase
import com.craftie.android.util.MockData
import com.craftie.android.utils.MainCoroutineRule
import com.craftie.android.utils.provideTestCoroutinesDispatcherProvider
import com.craftie.utils.Outcome
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
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
    var coroutineRule = MainCoroutineRule()

    private val dispatcher = provideTestCoroutinesDispatcherProvider(coroutineRule.testDispatcher)

    @Before
    fun setup() {
        discoveryViewModel = DiscoveryViewModel(dispatcher, discoveryUseCase)
    }

    @Test
    fun `test ui data returns success`() = runBlockingTest {
        coEvery { discoveryUseCase.build() } returns flowOf(Outcome.Success(stubDiscoveryUiData()))

        discoveryViewModel.init()

        discoveryViewModel.uiState.test {
            withTimeout(1000) {
                val uiState = DiscoveryUiState(uiData = stubDiscoveryUiData())
                expectEvent() shouldBe Event.Item(uiState)
            }
        }
    }

    @Test
    fun `test ui data returns failure`() = runBlockingTest {
        coEvery { discoveryUseCase.build() } returns flowOf(Outcome.Error("Failed to retrieve data"))

        discoveryViewModel.init()

        discoveryViewModel.uiState.test {
            withTimeout(1000) {
                val uiState = DiscoveryUiState(isError = true)
                expectEvent() shouldBe Event.Item(uiState)
            }
        }
    }

    @Test
    fun `test ui data returns loading`() = runBlockingTest {
        coEvery { discoveryUseCase.build() } returns emptyFlow()

        discoveryViewModel.init()

        discoveryViewModel.uiState.test(timeout = Duration.seconds(5000)) {
            val loading = DiscoveryUiState(isLoading = true)
            expectEvent() shouldBe Event.Item(loading)
        }
    }

    private fun stubDiscoveryUiData(): DiscoveryUiData = DiscoveryUiData(MockData.breweries(), MockData.beers())
}