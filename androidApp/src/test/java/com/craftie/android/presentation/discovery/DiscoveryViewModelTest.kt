package com.craftie.android.presentation.discovery

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.presentation.discovery.DiscoveryUiData
import com.craftie.android.presentation.discovery.DiscoveryUiState
import com.craftie.android.presentation.discovery.DiscoveryUseCase
import com.craftie.android.presentation.discovery.DiscoveryViewModel
import com.craftie.android.util.MockData
import com.craftie.android.utils.MainCoroutineRule
import com.craftie.android.utils.StubData
import com.craftie.android.utils.provideTestCoroutinesDispatcherProvider
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
        val uiState = DiscoveryUiState.Success(stubDiscoveryUiData())
        coEvery { discoveryUseCase.build() } returns flowOf(uiState)

        discoveryViewModel.init()

        discoveryViewModel.uiState.test {
            withTimeout(1000) {
                expectEvent() shouldBe Event.Item(uiState)
            }
        }
    }

    @Test
    fun `test ui data returns failure`() = runBlockingTest {
        val uiState = DiscoveryUiState.Error
        coEvery { discoveryUseCase.build() } returns flowOf(uiState)

        discoveryViewModel.init()

        discoveryViewModel.uiState.test {
            withTimeout(1000) {
                expectEvent() shouldBe Event.Item(uiState)
            }
        }
    }

    @Test
    fun `test ui data returns loading`() = runBlockingTest {
        discoveryViewModel.init()

        discoveryViewModel.uiState.test(timeout = Duration.seconds(5000)) {
            val loading = DiscoveryUiState.Loading
            expectEvent() shouldBe Event.Item(loading)
        }
    }

    private fun stubDiscoveryUiData(): DiscoveryUiData = DiscoveryUiData(
        StubData.breweries(),
        StubData.beers(),
        StubData.provinces()
    )
}