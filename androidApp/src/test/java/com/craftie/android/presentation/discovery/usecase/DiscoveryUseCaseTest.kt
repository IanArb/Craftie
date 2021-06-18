package com.craftie.android.presentation.discovery.usecase

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.presentation.discovery.model.DiscoveryUiData
import com.craftie.android.util.MockData
import com.craftie.android.utils.MainCoroutineRule
import com.craftie.android.utils.provideTestCoroutinesDispatcherProvider
import com.craftie.data.repository.CraftieBeersRepository
import com.craftie.data.repository.CraftieBreweriesRepository
import com.craftie.android.util.Outcome
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class DiscoveryUseCaseTest {

    private val beersRepository: CraftieBeersRepository = mockk()
    private val breweriesRepository: CraftieBreweriesRepository = mockk()

    private lateinit var discoveryUseCase: DiscoveryUseCase

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val dispatcher = provideTestCoroutinesDispatcherProvider(coroutineRule.testDispatcher)

    @Before
    fun setup() {
        discoveryUseCase = DiscoveryUseCase(beersRepository, breweriesRepository, dispatcher)
    }

    @Test
    fun `test build success when both beers and breweries are returned`() = runBlockingTest {
        val beers = MockData.beers()
        val breweries = MockData.breweries()

        coEvery { beersRepository.beers() } returns beers
        coEvery { breweriesRepository.breweries() } returns breweries

        discoveryUseCase.build().test {
            val discoveryUiData = DiscoveryUiData(
                breweries,
                beers
            )
            expectEvent() shouldBe Event.Item(Outcome.Success(discoveryUiData))
            expectComplete()
        }
    }

    @Test
    fun `test build failure when beers is not returned`() = runBlockingTest {
        val breweries = MockData.breweries()

        coEvery { beersRepository.beers() } returns emptyList()
        coEvery { breweriesRepository.breweries() } returns breweries

        discoveryUseCase.build().test {
            expectEvent() shouldBe Event.Item(Outcome.Error("Failed to retrieve data"))
            expectComplete()
        }
    }

    @Test
    fun `test build failure when breweries is not returned`() = runBlockingTest {
        val beers = MockData.beers()

        coEvery { beersRepository.beers() } returns beers
        coEvery { breweriesRepository.breweries() } returns emptyList()

        discoveryUseCase.build().test {
            expectEvent() shouldBe Event.Item(Outcome.Error("Failed to retrieve data"))
            expectComplete()
        }
    }

}