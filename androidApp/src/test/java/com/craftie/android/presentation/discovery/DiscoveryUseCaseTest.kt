package com.craftie.android.presentation.discovery

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.authentication.TokenUseCase
import com.craftie.android.utils.CoroutineTestRule
import com.craftie.android.utils.StubData
import com.craftie.data.repository.CraftieBeersRepository
import com.craftie.data.repository.CraftieBreweriesRepository
import com.craftie.data.repository.CraftieProvincesRepository
import com.craftie.data.useCase.CraftieFilterUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
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
    private val filterUseCase: CraftieFilterUseCase = mockk()
    private val tokenUseCase: TokenUseCase = mockk()

    private lateinit var discoveryUseCase: DiscoveryUseCase

    @get:Rule
    var coroutineRule = CoroutineTestRule()

    @Before
    fun setup() {
        discoveryUseCase = DiscoveryUseCase(
            coroutineRule.testDispatcherProvider,
            beersRepository,
            breweriesRepository,
            provincesRepository,
            filterUseCase,
            tokenUseCase,
        )
    }

    @Test
    fun `test build success when both beers, breweries, featured beer and provinces are returned`() = runTest {
        val beers = StubData.beers()
        val breweries = StubData.breweries()
        val provinces = StubData.provinces()
        val featuredBeer = StubData.featuredBeer()
        val filteredBeerByDate = StubData.beers().sortedBy { it.creationDate }

        coEvery { beersRepository.beers() } returns beers
        coEvery { breweriesRepository.breweries() } returns breweries
        coEvery { provincesRepository.provinces() } returns provinces
        coEvery { beersRepository.featuredBeer() } returns featuredBeer
        coEvery { filterUseCase.filterByCreationDate(StubData.beers()) } returns StubData.beers().sortedBy { it.creationDate }

        discoveryUseCase.build().test {
            val discoveryUiData = DiscoveryUiData(
                breweries,
                beers,
                provinces,
                featuredBeer,
                filteredBeerByDate,
            )

            val uiState = DiscoveryUiState.Success(discoveryUiData)

            awaitEvent() shouldBe Event.Item(uiState)
            awaitComplete()
        }
    }

    @Test
    fun `test build failure when beers is not returned`() = runTest {
        val breweries = StubData.breweries()

        coEvery { beersRepository.beers() } throws IOException()
        coEvery { breweriesRepository.breweries() } returns breweries

        discoveryUseCase.build().test {
            val uiState = DiscoveryUiState.Error
            awaitEvent() shouldBe Event.Item(uiState)
            awaitComplete()
        }
    }

    @Test
    fun `test build failure when breweries is not returned`() = runTest {
        val beers = StubData.beers()

        coEvery { beersRepository.beers() } returns beers
        coEvery { breweriesRepository.breweries() } throws IOException()

        discoveryUseCase.build().test {
            val uiState = DiscoveryUiState.Error
            awaitEvent() shouldBe Event.Item(uiState)
            awaitComplete()
        }
    }

    @Test
    fun `test build failure when provinces is not returned`() = runTest {
        val beers = StubData.beers()
        val breweries = StubData.breweries()

        coEvery { beersRepository.beers() } returns beers
        coEvery { breweriesRepository.breweries() } returns breweries
        coEvery { provincesRepository.provinces() } throws IOException()

        discoveryUseCase.build().test {
            val uiState = DiscoveryUiState.Error
            awaitEvent() shouldBe Event.Item(uiState)
            awaitComplete()
        }
    }

    @Test
    fun `test build failure when featured beer is not returned`() = runTest {
        val beers = StubData.beers()
        val breweries = StubData.breweries()
        val provinces = StubData.provinces()

        coEvery { beersRepository.beers() } returns beers
        coEvery { breweriesRepository.breweries() } returns breweries
        coEvery { provincesRepository.provinces() } returns provinces
        coEvery { beersRepository.featuredBeer() } throws IOException()

        discoveryUseCase.build().test {
            val uiState = DiscoveryUiState.Error
            awaitEvent() shouldBe Event.Item(uiState)
            awaitComplete()
        }
    }

}