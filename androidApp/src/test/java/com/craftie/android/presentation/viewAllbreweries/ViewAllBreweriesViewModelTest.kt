package com.craftie.android.presentation.viewAllbreweries

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.presentation.viewAllBreweries.ViewAllBreweriesUiState
import com.craftie.android.presentation.viewAllBreweries.ViewAllBreweriesUseCase
import com.craftie.android.presentation.viewAllBreweries.ViewAllBreweriesViewModel
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
class ViewAllBreweriesViewModelTest {

    private val useCase: ViewAllBreweriesUseCase = mockk()

    private lateinit var viewModel: ViewAllBreweriesViewModel

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val dispatcher = provideTestCoroutinesDispatcherProvider(coroutineRule.testDispatcher)

    @Before
    fun setup() {
        viewModel = ViewAllBreweriesViewModel(dispatcher, useCase)
    }

    @Test
    fun `test that ui state is loading`() = coroutineRule.runBlocking {
        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(ViewAllBreweriesUiState.Loading)
        }
    }

    @Test
    fun `test that ui state is error`() = coroutineRule.runBlocking {
        coEvery { useCase.breweries() } returns flowOf(ViewAllBreweriesUiState.Error)

        viewModel.init()

        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(ViewAllBreweriesUiState.Error)
        }
    }

    @Test
    fun `test that ui state is success`() {
        val uiState = ViewAllBreweriesUiState.Success(StubData.breweries())
        coroutineRule.runBlocking {
            coEvery { useCase.breweries() } returns flowOf(uiState)

            viewModel.init()

            viewModel.uiState.test {
                expectEvent() shouldBe Event.Item(uiState)
            }
        }
    }
}