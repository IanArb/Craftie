package com.craftie.android.presentation.beerDetail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.util.MockData
import com.craftie.android.utils.*
import com.craftie.data.repository.FavouritesRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class BeerDetailViewModelTest {

    private val useCase: BeerDetailUseCase = mockk()

    private val savedState: SavedStateHandle = mockk()

    private val repository: FavouritesRepository = mockk()

    private lateinit var viewModel: BeerDetailViewModel

    @get:Rule
    var coroutineRule = CoroutineTestRule()

    @Before
    fun setup() {
        viewModel = BeerDetailViewModel(
            useCase,
            savedState,
            coroutineRule.testDispatcherProvider,
            repository
        )
    }

    @Test
    fun `test that ui state should be success`() = runTest {
        val beer = StubData.beers().first()
        coEvery { useCase.beer("1") } returns flowOf(BeerDetailUiState.Success(beer))
        every { savedState.get<String>(Constants.BEER_ID_KEY) } returns "1"
        every { savedState.get<String>(Constants.SEARCH_RESULT_ID_KEY) } returns ""

        viewModel.init()

        viewModel.uiState.test {
            awaitEvent() shouldBe Event.Item(BeerDetailUiState.Success(beer))
        }
    }

    @Test
    fun `test that ui state should be error`() = runTest {
        coEvery { useCase.beer("1") } returns flowOf(BeerDetailUiState.Error)
        every { savedState.get<String>(Constants.BEER_ID_KEY) } returns "1"
        every { savedState.get<String>(Constants.SEARCH_RESULT_ID_KEY) } returns ""

        viewModel.init()

        viewModel.uiState.test {
            awaitEvent() shouldBe Event.Item(BeerDetailUiState.Error)
        }
    }

    @Test
    fun `test that ui state should be loading`() = runTest {
        viewModel.init()

        viewModel.uiState.test {
            awaitEvent() shouldBe Event.Item(BeerDetailUiState.Loading)
        }
    }

    @Test
    fun `test beer is saved`() = runTest {
        val beer = MockData.beers().first()

        coEvery { repository.saveBeer(beer) } returns Unit

        viewModel.saveBeer(beer)

        coVerify { repository.saveBeer(any()) }
    }
}