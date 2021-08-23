package com.craftie.android.presentation.discovery

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.presentation.discovery.DiscoveryUiData
import com.craftie.android.presentation.discovery.DiscoveryUiState
import com.craftie.android.presentation.discovery.DiscoveryUseCase
import com.craftie.android.util.MockData
import com.craftie.android.utils.MainCoroutineRule
import com.craftie.android.utils.StubData
import com.craftie.android.utils.provideTestCoroutinesDispatcherProvider
import com.craftie.data.repository.CraftieBeersRepository
import com.craftie.data.repository.CraftieBreweriesRepository
import com.craftie.data.repository.CraftieProvincesRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import kotlin.time.ExperimentalTime

@ExperimentalTime
class DiscoveryUseCaseTest {

    private val beersRepository: CraftieBeersRepository = mockk()
    private val breweriesRepository: CraftieBreweriesRepository = mockk()
    private val provincesRepository: CraftieProvincesRepository = mockk()

    private lateinit var discoveryUseCase: DiscoveryUseCase

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val dispatcher = provideTestCoroutinesDispatcherProvider(coroutineRule.testDispatcher)

    @Before
    fun setup() {
        discoveryUseCase = DiscoveryUseCase(
            beersRepository,
            breweriesRepository,
            provincesRepository,
            dispatcher
        )
    }

    @Test
    fun `test build success when both beers, breweries and provinces are returned`() = runBlockingTest {
        val beers = StubData.beers()
        val breweries = StubData.breweries()
        val provinces = StubData.provinces()

        coEvery { beersRepository.beers() } returns beers
        coEvery { breweriesRepository.breweries() } returns breweries
        coEvery { provincesRepository.provinces() } returns provinces

        discoveryUseCase.build().test {
            val discoveryUiData = DiscoveryUiData(
                breweries,
                beers,
                provinces
            )

            val uiState = DiscoveryUiState.Success(discoveryUiData)

            expectEvent() shouldBe Event.Item(uiState)
            expectComplete()
        }
    }

    @Test
    fun `test build failure when beers is not returned`() = runBlockingTest {
        val breweries = MockData.breweries()

        coEvery { beersRepository.beers() } throws IOException()
        coEvery { breweriesRepository.breweries() } returns breweries

        discoveryUseCase.build().test {
            val uiState = DiscoveryUiState.Error
            expectEvent() shouldBe Event.Item(uiState)
            expectComplete()
        }
    }

    @Test
    fun `test build failure when breweries is not returned`() = runBlockingTest {
        val beers = MockData.beers()

        coEvery { beersRepository.beers() } returns beers
        coEvery { breweriesRepository.breweries() } throws IOException()

        discoveryUseCase.build().test {
            val uiState = DiscoveryUiState.Error
            expectEvent() shouldBe Event.Item(uiState)
            expectComplete()
        }
    }

    @Test
    fun `test build failure when provinces is not returned`() = runBlockingTest {
        val beers = StubData.beers()
        val breweries = StubData.breweries()

        coEvery { beersRepository.beers() } returns beers
        coEvery { breweriesRepository.breweries() } returns breweries
        coEvery { provincesRepository.provinces() } throws IOException()

        discoveryUseCase.build().test {
            val uiState = DiscoveryUiState.Error
            expectEvent() shouldBe Event.Item(uiState)
            expectComplete()
        }
    }

}