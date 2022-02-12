package com.craftie.android.presentation.viewAllRatings

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.presentation.ratings.RatingUiState
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
class ViewAllRatingsUseCaseTest {

    private val repository: CraftieBeerRatingsRepository = mockk()

    private lateinit var useCase: ViewAllRatingsUseCase

    @Before
    fun setup() {
        useCase = ViewAllRatingsUseCase(repository)
    }

    @Test
    fun `test that build ratings returns successfully`() = runTest {
        val ratings = RatingsStubData.ratings()

        coEvery { repository.ratingsByBeerId("1") } returns ratings

        useCase.build("1").test {
            awaitEvent() shouldBe Event.Item(ViewAllRatingsUiState.Success(ratings))
            awaitComplete()
        }
    }

    @Test
    fun `test that build ratings returns error`() = runTest {
        coEvery { repository.ratingsByBeerId("1") } throws IOException()

        useCase.build("1").test {
            awaitEvent() shouldBe Event.Item(ViewAllRatingsUiState.Error)
            awaitComplete()
        }
    }
}