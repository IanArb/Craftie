package com.craftie.android.presentation.home

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.authentication.TokenUseCase
import com.craftie.android.utils.CoroutineTestRule
import com.craftie.android.utils.StubData
import com.craftie.data.repository.FavouritesRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class HomeViewModelTest {

    private val favouritesRepository: FavouritesRepository = mockk()

    private val tokenUseCase: TokenUseCase = mockk()

    private val favouriteBeersUseCase: FavouriteBeersUseCase = mockk()

    private lateinit var homeViewModel: HomeViewModel

    @get:Rule
    var coroutineRule = CoroutineTestRule()

    @Before
    fun setup() {
        homeViewModel = HomeViewModel(
            favouriteBeersUseCase = favouriteBeersUseCase,
            favouritesRepository = favouritesRepository,
            tokenUseCase = tokenUseCase,
            dispatcherProvider = coroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun `test fetch favourites`() = runTest {
        coEvery { favouritesRepository.findAllBeers() } returns flowOf(StubData.favourites())

        homeViewModel.fetchFavourites()

        homeViewModel.favourites.test {
            awaitEvent() shouldBe Event.Item(StubData.favourites())
        }
    }

    @Test
    fun `test login ui state returns success`() = runTest {
        coEvery { tokenUseCase.login() } returns flowOf(LoginUiState.Success)

        homeViewModel.login()

        homeViewModel.loginUiState.test {
            awaitEvent() shouldBe Event.Item(LoginUiState.Success)
        }
    }

    @Test
    fun `test login ui state returns error`() = runTest {
        coEvery { tokenUseCase.login() } returns flowOf(LoginUiState.Error)

        homeViewModel.login()

        homeViewModel.loginUiState.test {
            awaitEvent() shouldBe Event.Item(LoginUiState.Error)
        }
    }

    @Test
    fun `test login ui state returns loading`() = runTest {
        coEvery { tokenUseCase.login() } returns flowOf(LoginUiState.Loading)

        homeViewModel.loginUiState.test {
            awaitEvent() shouldBe Event.Item(LoginUiState.Loading)
        }
    }

    @Test
    fun `test favourite beers percentage returns success`() = runTest {
        coEvery { favouriteBeersUseCase.fetchFavouriteBeersByProvince(StubData.favourites()) } returns flowOf(FavouriteBeersUiState.Success(StubData.favouriteBeersUiData()))

        homeViewModel.fetchBeersTasted(StubData.favourites())

        homeViewModel.beersFavourites.test {
            awaitEvent() shouldBe Event.Item(FavouriteBeersUiState.Success(StubData.favouriteBeersUiData()))
        }
    }

    @Test
    fun `test favourite beers percentage returns error`() = runTest {
        coEvery { favouriteBeersUseCase.fetchFavouriteBeersByProvince(StubData.favourites()) } returns flowOf(FavouriteBeersUiState.Error)

        homeViewModel.fetchBeersTasted(StubData.favourites())

        homeViewModel.beersFavourites.test {
            awaitEvent() shouldBe Event.Item(FavouriteBeersUiState.Error)
        }
    }

    @Test
    fun `test favourite beers percentage returns loading`() = runTest {
        coEvery { favouriteBeersUseCase.fetchFavouriteBeersByProvince(StubData.favourites()) } returns flowOf(FavouriteBeersUiState.Loading)

        homeViewModel.beersFavourites.test {
            awaitEvent() shouldBe Event.Item(FavouriteBeersUiState.Loading)
        }
    }
}