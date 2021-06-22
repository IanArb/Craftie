package com.craftie.android.utils

import com.craftie.android.util.CoroutinesDispatcherProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
fun provideTestCoroutinesDispatcherProvider(
    dispatcher: TestCoroutineDispatcher?
): CoroutinesDispatcherProvider {
    val sharedTestCoroutineDispatcher = TestCoroutineDispatcher()
    return CoroutinesDispatcherProvider(
        dispatcher ?: sharedTestCoroutineDispatcher,
        dispatcher ?: sharedTestCoroutineDispatcher,
        dispatcher ?: sharedTestCoroutineDispatcher
    )
}

@ExperimentalCoroutinesApi
class MainCoroutineRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}

@ExperimentalCoroutinesApi
fun MainCoroutineRule.runBlocking(block: suspend () -> Unit) = this.testDispatcher.runBlockingTest {
    block()
}

fun provideCoroutineScope(): TestCoroutineScope {
    return TestCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
}

class TestCoroutineScope(context: CoroutineContext) : Closeable, CoroutineScope {
    override val coroutineContext: CoroutineContext = context

    override fun close() {
        coroutineContext.cancel()
    }
}