package com.craftie.android.presentation.recentSearches

import app.cash.turbine.Event
import app.cash.turbine.test
import com.craftie.android.presentation.recentsearches.RecentSearchesViewModel
import com.craftie.android.utils.CoroutineTestRule
import com.craftie.android.utils.StubData
import com.craftie.data.repository.RecentSearchesRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RecentSearchesViewModelTest {

    private val recentSearchesRepository: RecentSearchesRepository = mockk()

    private lateinit var viewModel: RecentSearchesViewModel

    @get:Rule
    var coroutineRule = CoroutineTestRule()

    @Before
    fun setup() {
        viewModel = RecentSearchesViewModel(
            recentSearchesRepository = recentSearchesRepository,
            dispatcherProvider = coroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun `test recent searches is not empty`() = runTest {
        coEvery { recentSearchesRepository.findAllRecentSearches() } returns flowOf(StubData.recentSearches())

        viewModel.init()

        viewModel.recentSearches.test {
            awaitEvent() shouldBe Event.Item(StubData.recentSearches())
        }
    }

    @Test
    fun `test recent searches is empty`() = runTest {
        coEvery { recentSearchesRepository.findAllRecentSearches() } returns flowOf(emptyList())

        viewModel.init()

        viewModel.recentSearches.test {
            awaitEvent() shouldBe Event.Item(emptyList())
        }
    }

    @Test
    fun `test remove recent search by id`() = runTest {
        coEvery { recentSearchesRepository.removeRecentSearch("1") } returns Unit

        viewModel.removeRecentSearch("1")

        coVerify { viewModel.removeRecentSearch("1") }
    }

    @Test
    fun `test remove all recent searches`() = runTest {
        coEvery { recentSearchesRepository.removeAllRecentSearches() } returns Unit

        viewModel.removeAllRecentSearches()

        coVerify { viewModel.removeAllRecentSearches() }
    }

    @Test
    fun `test save recent search`() = runTest {
        coEvery { recentSearchesRepository.saveRecentSearch("1", "Rascals") } returns Unit

        viewModel.addRecentSearch("1", "Rascals")

        coVerify { viewModel.addRecentSearch("1", "Rascals") }
    }
}