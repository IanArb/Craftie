package com.craftie.android.presentation.ratings

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.data.model.RatingRequest
import com.craftie.data.model.RatingResult
import com.craftie.data.repository.CraftieBeerRatingsRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.time.ExperimentalTime

@ExperimentalTime
class SaveBeerRatingUseCaseTest {

    private val repository: CraftieBeerRatingsRepository = mockk()

    private lateinit var useCase: SaveBeerRatingUseCase

    @Before
    fun setup() {
        useCase = SaveBeerRatingUseCase(repository)
    }

    @Test
    fun `test save beer rating is success`() = runTest {
        val ratingRequest = RatingStubs.ratingRequest()
        val message = "Successfully sent request"
        coEvery { repository.saveRating(ratingRequest) } returns message

        useCase.saveRating(ratingRequest).test {
            awaitEvent() shouldBe Event.Item(SendRatingUiState.Success(message))
            awaitComplete()
        }
    }

    @Test
    fun `test save beer rating is error`() = runTest {
        val ratingRequest = RatingStubs.ratingRequest()
        coEvery { repository.saveRating(ratingRequest) } throws IOException()

        useCase.saveRating(ratingRequest).test {
            awaitEvent() shouldBe Event.Item(SendRatingUiState.Error)
            awaitComplete()
        }
    }

    @Test
    fun `test beer ratings returns successfully`() = runTest {
        val rating = RatingStubs.rating()

        coEvery { repository.rating("1") } returns rating

        useCase.ratingByBeerId("1").test {
            awaitEvent() shouldBe Event.Item(RatingUiState.Success(rating))
            awaitComplete()
        }
    }

    @Test
    fun `test beer ratings returns error`() = runTest {
        coEvery { repository.rating("1") } throws IOException()

        useCase.ratingByBeerId("1").test {
            awaitEvent() shouldBe Event.Item(RatingUiState.Error)
            awaitComplete()
        }
    }

}

object RatingStubs {

    fun ratingRequest() = RatingRequest(
        "123456",
        "",
        "",
        5.0
    )

    fun rating() = RatingResult(
        4.0,
        10
    )

}