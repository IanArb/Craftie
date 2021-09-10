package com.craftie.android.presentation.search

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.utils.MainCoroutineRule
import com.craftie.android.utils.StubData
import com.craftie.android.utils.provideTestCoroutinesDispatcherProvider
import com.craftie.android.utils.runBlocking
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class SearchFilterViewModelTest {

    private val searchFilterUseCase: SearchFilterUseCase = mockk()

    private lateinit var viewModel: SearchFilterViewModel

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val dispatcher = provideTestCoroutinesDispatcherProvider(coroutineRule.testDispatcher)

    @Before
    fun setup() {
        viewModel = SearchFilterViewModel(
            searchFilterUseCase,
            dispatcher
        )
    }

    @Test
    fun `test query by keyword returns success`() = coroutineRule.runBlocking {
        val aleBeers = StubData.aleBeers()
        val uiState = SearchFilterUiState.Success(aleBeers)

        val keyword = flowOf("Ale")

        coEvery { searchFilterUseCase.findBeersByKeyword("Ale") } returns flowOf(uiState)

        viewModel.queryBeers(keyword)

        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(uiState)
        }
    }

    @Test
    fun `test query by keyword returns idle state`() = coroutineRule.runBlocking {
        val uiState = SearchFilterUiState.Idle

        coEvery { searchFilterUseCase.findBeersByKeyword("") } returns flowOf(uiState)

        viewModel.queryBeers(flowOf(""))

        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(uiState)
        }
    }

    @Test
    fun `test query by keyword returns error`() = coroutineRule.runBlocking {
        val uiState = SearchFilterUiState.Error
        coEvery { searchFilterUseCase.findBeersByKeyword("Ale") } returns flowOf(uiState)

        val keyword = flowOf("Ale")

        viewModel.queryBeers(keyword)

        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(uiState)
        }
    }

    @Test
    fun `test query by keyword returns empty`() = coroutineRule.runBlocking {
        val uiState = SearchFilterUiState.Empty
        coEvery { searchFilterUseCase.findBeersByKeyword("Lager") } returns flowOf(uiState)

        val keyword = flowOf("Lager")

        viewModel.queryBeers(keyword)

        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(uiState)
        }
    }

    @Test
    fun `test query by keyword returns loading state`() = coroutineRule.runBlocking {
        val uiState = SearchFilterUiState.Loading
        coEvery { searchFilterUseCase.findBeersByKeyword("Lager") } returns flowOf(uiState)

        val keyword = flowOf("Lager")

        viewModel.queryBeers(keyword)

        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(uiState)
        }
    }
}