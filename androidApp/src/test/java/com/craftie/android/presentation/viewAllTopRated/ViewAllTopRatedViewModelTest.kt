package com.craftie.android.presentation.viewAllTopRated

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.utils.*
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class ViewAllTopRatedViewModelTest {

    private val useCase: ViewAllTopRatedUseCase = mockk()

    private lateinit var viewModel: ViewAllTopRatedViewModel

    @get:Rule
    var coroutineRule = CoroutineTestRule()

    @Before
    fun setup() {
        viewModel = ViewAllTopRatedViewModel(useCase, coroutineRule.testDispatcherProvider)
    }

    @Test
    fun `test that ui state is loading`() = runTest {
        viewModel.uiState.test {
            awaitEvent() shouldBe Event.Item(ViewAllTopRatedUiState.Loading)
        }
    }

    @Test
    fun `test that ui state is error`() = runTest {
        coEvery { useCase.beers() } returns flowOf(ViewAllTopRatedUiState.Error)

        viewModel.init()

        viewModel.uiState.test {
            awaitEvent() shouldBe Event.Item(ViewAllTopRatedUiState.Error)
        }
    }

    @Test
    fun `test that ui state is success`() {
        val uiState = ViewAllTopRatedUiState.Success(StubData.beers())
        runTest {
            coEvery { useCase.beers() } returns flowOf(uiState)

            viewModel.init()

            viewModel.uiState.test {
                awaitEvent() shouldBe Event.Item(uiState)
            }
        }
    }
}