package com.craftie.android.presentation.ratings

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.utils.Constants
import com.craftie.android.utils.MainCoroutineRule
import com.craftie.android.utils.provideTestCoroutinesDispatcherProvider
import com.craftie.android.utils.runBlocking
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
class SaveBeerRatingViewModelTest {

    private val useCase: SaveBeerRatingUseCase = mockk()

    private lateinit var viewModel: SaveBeerRatingViewModel

    private val savedStateHandle: SavedStateHandle = mockk()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val dispatcher = provideTestCoroutinesDispatcherProvider(coroutineRule.testDispatcher)

    @Before
    fun setup() {
        viewModel = SaveBeerRatingViewModel(
            useCase,
            savedStateHandle,
            dispatcher
        )
    }

    @Test
    fun `test that ui state is idle`() = coroutineRule.runBlocking {
        viewModel.sendRatingUiState.test {
            awaitEvent() shouldBe Event.Item(SendRatingUiState.Idle)
        }
    }

    @Test
    fun `test that ui state is success`() = coroutineRule.runBlocking {
        every { savedStateHandle.get<String>(Constants.BEER_ID_KEY) } returns "123456"
        val message = "Successfully sent request"
        coEvery { useCase.saveRating(RatingStubs.ratingRequest()) } returns flowOf(SendRatingUiState.Success(
            message
        ))

        viewModel.sendRating(RatingStubs.ratingRequest())

        viewModel.sendRatingUiState.test {
            awaitEvent() shouldBe Event.Item(SendRatingUiState.Success(message))
        }

    }

    @Test
    fun `test that ui state is error`() = coroutineRule.runBlocking {
        every { savedStateHandle.get<String>(Constants.BEER_ID_KEY) } returns "123456"
        coEvery { useCase.saveRating(RatingStubs.ratingRequest()) } returns flowOf(SendRatingUiState.Error)

        viewModel.sendRating(RatingStubs.ratingRequest())

        viewModel.sendRatingUiState.test {
            awaitEvent() shouldBe Event.Item(SendRatingUiState.Error)
        }

    }

    @Test
    fun `test that ui state is loading`() = coroutineRule.runBlocking {
        every { savedStateHandle.get<String>(Constants.BEER_ID_KEY) } returns "123456"
        coEvery { useCase.saveRating(RatingStubs.ratingRequest()) } returns flowOf(SendRatingUiState.Loading)

        viewModel.sendRating(RatingStubs.ratingRequest())

        viewModel.sendRatingUiState.test {
            awaitEvent() shouldBe Event.Item(SendRatingUiState.Loading)
        }

    }

    @Test
    fun `test that rating ui state is loading`() = coroutineRule.runBlocking {
        viewModel.ratingUiState.test {
            awaitEvent() shouldBe Event.Item(RatingUiState.Loading)
        }

    }

    @Test
    fun `test that rating ui state is success`() = coroutineRule.runBlocking {
        val id = "12345"

        every { savedStateHandle.get<String>(Constants.BEER_ID_KEY) } returns id

        val rating = RatingStubs.rating()

        coEvery { useCase.ratingByBeerId(id) } returns flowOf(RatingUiState.Success(rating))

        viewModel.fetchRating()

        viewModel.ratingUiState.test {
            awaitEvent() shouldBe Event.Item(RatingUiState.Success(rating))
        }
    }

    @Test
    fun `test that rating ui state is erorr`() = coroutineRule.runBlocking {
        val id = "12345"
        every { savedStateHandle.get<String>(Constants.BEER_ID_KEY) } returns id

        coEvery { useCase.ratingByBeerId(id) } returns flowOf(RatingUiState.Error)

        viewModel.fetchRating()

        viewModel.ratingUiState.test {
            awaitEvent() shouldBe Event.Item(RatingUiState.Error)
        }
    }
}