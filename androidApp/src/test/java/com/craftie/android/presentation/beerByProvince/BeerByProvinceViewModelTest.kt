package com.craftie.android.presentation.beerByProvince

import androidx.lifecycle.SavedStateHandle
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
class BeerByProvinceViewModelTest {

    private val useCase: BeersByProvinceUseCase = mockk()

    private val savedStateHandle: SavedStateHandle = mockk()

    private lateinit var viewModel: BeersByProvinceViewModel

    @get:Rule
    var coroutineRule = CoroutineTestRule()


    @Before
    fun setup() {
        viewModel = BeersByProvinceViewModel(useCase, savedStateHandle, coroutineRule.testDispatcherProvider)
    }

    @Test
    fun `test that ui state is loading`() = runTest {
        viewModel.uiState.test {
            awaitEvent() shouldBe Event.Item(BeersByProvinceUiState.Loading)
        }
    }

    @Test
    fun `test that ui state is success`() = runTest {
        val beers = StubData.beers()
        coEvery { useCase.beersByProvince("Leinster") } returns flowOf(BeersByProvinceUiState.Success(beers))
        coEvery { savedStateHandle.get<String>("province") } returns "Leinster"

        viewModel.init()

        viewModel.uiState.test {
            awaitEvent() shouldBe Event.Item(BeersByProvinceUiState.Success(beers))
        }
    }

    @Test
    fun `test that ui state is empty`() = runTest {
        coEvery { useCase.beersByProvince("Ulster") } returns flowOf(BeersByProvinceUiState.Empty)
        coEvery { savedStateHandle.get<String>("province") } returns "Ulster"

        viewModel.init()

        viewModel.uiState.test {
            awaitEvent() shouldBe Event.Item(BeersByProvinceUiState.Empty)
        }
    }

    @Test
    fun `test that ui state is error`() = runTest {
        coEvery { useCase.beersByProvince("Leinster") } returns flowOf(BeersByProvinceUiState.Error)
        coEvery { savedStateHandle.get<String>("province") } returns "Leinster"

        viewModel.init()

        viewModel.uiState.test {
            awaitEvent() shouldBe Event.Item(BeersByProvinceUiState.Error)
        }
    }
}