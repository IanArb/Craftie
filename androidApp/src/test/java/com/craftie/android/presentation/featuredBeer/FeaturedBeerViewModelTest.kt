package com.craftie.android.presentation.featuredBeer

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.presentation.featuredBeer.FeaturedBeerUiState
import com.craftie.android.presentation.featuredBeer.FeaturedBeerUseCase
import com.craftie.android.presentation.featuredBeer.FeaturedBeerViewModel
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
class FeaturedBeerViewModelTest {

    private val featuredBeerUseCase: FeaturedBeerUseCase = mockk()

    private lateinit var viewModel: FeaturedBeerViewModel

    @get:Rule
    var coroutineRule = CoroutineTestRule()

    @Before
    fun setup() {
        viewModel = FeaturedBeerViewModel(featuredBeerUseCase, coroutineRule.testDispatcherProvider)
    }

    @Test
    fun `test that ui state should be success`() = runTest {
        val uiState = FeaturedBeerUiState.Success(StubData.featuredBeer())
        coEvery { featuredBeerUseCase.featuredBeer() } returns flowOf(uiState)

        viewModel.init()

        viewModel.uiState.test {
            awaitEvent() shouldBe Event.Item(uiState)
        }
    }

    @Test
    fun `test that ui state should be error`() = runTest{
        val uiState = FeaturedBeerUiState.Error
        coEvery { featuredBeerUseCase.featuredBeer() } returns flowOf(uiState)

        viewModel.init()

        viewModel.uiState.test {
            awaitEvent() shouldBe Event.Item(uiState)
        }
    }

    @Test
    fun `test that ui state should be loading`() = runTest {
        val uiState = FeaturedBeerUiState.Loading

        viewModel.uiState.test {
            awaitEvent() shouldBe Event.Item(uiState)
        }
    }
}