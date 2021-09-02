package com.craftie.android.presentation.home

import com.craftie.android.utils.MainCoroutineRule
import com.craftie.android.utils.provideTestCoroutinesDispatcherProvider
import com.craftie.data.repository.DatabaseRepository
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule

//TODO Figure out how to mock RealmResult object
class HomeViewModelTest {

    private val repository: DatabaseRepository = mockk()

    lateinit var viewModel: HomeViewModel

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val dispatcher = provideTestCoroutinesDispatcherProvider(coroutineRule.testDispatcher)

    @Before
    fun setup() {
        viewModel = HomeViewModel(repository, dispatcher)
    }


}