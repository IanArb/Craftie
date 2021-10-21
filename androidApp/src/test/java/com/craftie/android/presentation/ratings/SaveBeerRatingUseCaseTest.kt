package com.craftie.android.presentation.ratings

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.data.model.RatingRequest
import com.craftie.data.repository.CraftieBeerRatingsRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
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
    fun `test save beer rating is success`() = runBlockingTest {
        val ratingRequest = RatingStubs.ratingRequest()
        val message = "Successfully sent request"
        coEvery { repository.saveRating(ratingRequest) } returns message

        useCase.saveRating(ratingRequest).test {
            expectEvent() shouldBe Event.Item(SendRatingUiState.Success(message))
            expectComplete()
        }
    }

    @Test
    fun `test save beer rating is error`() = runBlockingTest {
        val ratingRequest = RatingStubs.ratingRequest()
        coEvery { repository.saveRating(ratingRequest) } throws IOException()

        useCase.saveRating(ratingRequest).test {
            expectEvent() shouldBe Event.Item(SendRatingUiState.Error)
            expectComplete()
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

}