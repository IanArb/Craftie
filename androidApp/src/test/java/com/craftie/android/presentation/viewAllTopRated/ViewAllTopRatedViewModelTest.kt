package com.craftie.android.presentation.viewAllTopRated

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
class ViewAllTopRatedViewModelTest {

    private val useCase: ViewAllTopRatedUseCase = mockk()

    private lateinit var viewModel: ViewAllTopRatedViewModel

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val dispatcher = provideTestCoroutinesDispatcherProvider(coroutineRule.testDispatcher)

    @Before
    fun setup() {
        viewModel = ViewAllTopRatedViewModel(useCase, dispatcher)
    }

    @Test
    fun `test that ui state is loading`() = coroutineRule.runBlocking {
        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(ViewAllTopRatedUiState.Loading)
        }
    }

    @Test
    fun `test that ui state is error`() = coroutineRule.runBlocking {
        coEvery { useCase.beers() } returns flowOf(ViewAllTopRatedUiState.Error)

        viewModel.init()

        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(ViewAllTopRatedUiState.Error)
        }
    }

    @Test
    fun `test that ui state is success`() {
        val uiState = ViewAllTopRatedUiState.Success(StubData.beers())
        coroutineRule.runBlocking {
            coEvery { useCase.beers() } returns flowOf(uiState)

            viewModel.init()

            viewModel.uiState.test {
                expectEvent() shouldBe Event.Item(uiState)
            }
        }
    }
}