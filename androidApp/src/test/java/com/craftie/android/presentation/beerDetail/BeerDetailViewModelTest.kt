package com.craftie.android.presentation.beerDetail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.utils.*
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class BeerDetailViewModelTest {

    private val useCase: BeerDetailUseCase = mockk()

    private val savedState: SavedStateHandle = mockk()

    private lateinit var viewModel: BeerDetailViewModel

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val dispatcher = provideTestCoroutinesDispatcherProvider(coroutineRule.testDispatcher)

    @Before
    fun setup() {
        viewModel = BeerDetailViewModel(
            useCase,
            savedState,
            dispatcher
        )
    }

    @Test
    fun `test that ui state should be success`() = coroutineRule.runBlocking {
        val beer = StubData.beers().first()
        coEvery { useCase.beer("1") } returns flowOf(BeerDetailUiState.Success(beer))
        every { savedState.get<String>(Constants.BEER_ID_KEY) } returns "1"

        viewModel.init()

        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(BeerDetailUiState.Success(beer))
        }
    }

    @Test
    fun `test that ui state should be error`() = coroutineRule.runBlocking {
        coEvery { useCase.beer("1") } returns flowOf(BeerDetailUiState.Error)
        every { savedState.get<String>(Constants.BEER_ID_KEY) } returns "1"

        viewModel.init()

        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(BeerDetailUiState.Error)
        }
    }

    @Test
    fun `test that ui state should be loading`() = coroutineRule.runBlocking {
        viewModel.init()

        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(BeerDetailUiState.Loading)
        }
    }
}