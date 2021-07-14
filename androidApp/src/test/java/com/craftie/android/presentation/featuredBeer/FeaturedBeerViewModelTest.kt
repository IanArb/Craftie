package com.craftie.android.presentation.featuredBeer

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.presentation.featuredBeer.FeaturedBeerUiState
import com.craftie.android.presentation.featuredBeer.FeaturedBeerUseCase
import com.craftie.android.presentation.featuredBeer.FeaturedBeerViewModel
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
class FeaturedBeerViewModelTest {

    private val featuredBeerUseCase: FeaturedBeerUseCase = mockk()

    private lateinit var viewModel: FeaturedBeerViewModel

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val dispatcher = provideTestCoroutinesDispatcherProvider(coroutineRule.testDispatcher)

    @Before
    fun setup() {
        viewModel = FeaturedBeerViewModel(featuredBeerUseCase, dispatcher)
    }

    @Test
    fun `test that ui state should be success`() = coroutineRule.runBlocking {
        val uiState = FeaturedBeerUiState.Success(StubData.featuredBeer())
        coEvery { featuredBeerUseCase.featuredBeer() } returns flowOf(uiState)

        viewModel.init()

        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(uiState)
        }
    }

    @Test
    fun `test that ui state should be error`() = coroutineRule.runBlocking {
        val uiState = FeaturedBeerUiState.Error
        coEvery { featuredBeerUseCase.featuredBeer() } returns flowOf(uiState)

        viewModel.init()

        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(uiState)
        }
    }

    @Test
    fun `test that ui state should be loading`() = coroutineRule.runBlocking {
        val uiState = FeaturedBeerUiState.Loading

        viewModel.uiState.test {
            expectEvent() shouldBe Event.Item(uiState)
        }
    }
}