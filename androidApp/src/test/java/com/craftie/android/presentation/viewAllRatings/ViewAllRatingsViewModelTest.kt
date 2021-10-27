package com.craftie.android.presentation.viewAllRatings

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.utils.MainCoroutineRule
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
class ViewAllRatingsViewModelTest {

    private val useCase: ViewAllRatingsUseCase = mockk()

    private val savedStateHandle: SavedStateHandle = mockk()

    private lateinit var viewModel: ViewAllRatingsViewModel

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val dispatcher = provideTestCoroutinesDispatcherProvider(coroutineRule.testDispatcher)

    @Before
    fun setup() {
        viewModel = ViewAllRatingsViewModel(useCase, dispatcher, savedStateHandle)
    }

    @Test
    fun `test that ui state is success`() = coroutineRule.runBlocking {
        coEvery { savedStateHandle.get<String>("beerDetail") } returns "1"

        val ratings = RatingsStubData.ratings()
        coEvery { useCase.build("1") } returns flowOf(ViewAllRatingsUiState.Success(ratings))

        viewModel.fetchRatings()

        viewModel.uiState.test {
            awaitEvent() shouldBe Event.Item(ViewAllRatingsUiState.Success(ratings))
        }
    }

    @Test
    fun `test that ui state is error`() = coroutineRule.runBlocking {
        coEvery { savedStateHandle.get<String>("beerDetail") } returns "1"

        coEvery { useCase.build("1") } returns flowOf(ViewAllRatingsUiState.Error)

        viewModel.fetchRatings()

        viewModel.uiState.test {
            awaitEvent() shouldBe Event.Item(ViewAllRatingsUiState.Error)
        }
    }

    @Test
    fun `test that ui state is loading`() = coroutineRule.runBlocking {
        viewModel.uiState.test {
            awaitEvent() shouldBe Event.Item(ViewAllRatingsUiState.Loading)
        }
    }
}