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
        coEvery { searchFilterUseCase.findBeersByKeyword("Ale") } returns flowOf(uiState)

        viewModel.queryBeers("Ale")

        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(uiState)
        }
    }

    @Test
    fun `test query by keyword returns error`() = coroutineRule.runBlocking {
        val uiState = SearchFilterUiState.Error
        coEvery { searchFilterUseCase.findBeersByKeyword("Ale") } returns flowOf(uiState)

        viewModel.queryBeers("Ale")

        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(uiState)
        }
    }

    @Test
    fun `test query by keyword returns empty`() = coroutineRule.runBlocking {
        val uiState = SearchFilterUiState.Empty
        coEvery { searchFilterUseCase.findBeersByKeyword("") } returns flowOf(uiState)

        viewModel.queryBeers("")

        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(uiState)
        }
    }

    @Test
    fun `test loading ui state`() = coroutineRule.runBlocking {
        val uiState = SearchFilterUiState.Loading
        coEvery { searchFilterUseCase.findBeersByKeyword("") } returns flowOf(uiState)

        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(uiState)
        }
    }
}