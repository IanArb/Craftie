package com.craftie.android.utils

import com.craftie.android.util.CoroutinesDispatcherProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class CoroutineTestRule(private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())) : TestWatcher() {

    val testDispatcherProvider = CoroutinesDispatcherProvider(
        testDispatcher,
        testDispatcher,
        testDispatcher
    )

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}