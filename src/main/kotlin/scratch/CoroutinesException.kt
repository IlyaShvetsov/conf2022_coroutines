package scratch

import kotlinx.coroutines.*

private suspend fun foo() {
    val scope = CoroutineScope(Dispatchers.Default) //  + SupervisorJob()

    val job1 = scope.launch {
        launch {
            error("dfsf")
        }
    }

    val job2 = scope.launch {
        delay(1)
        println("job2")
    }

    val job3 = scope.launch {
        delay(1)
        println("job3")
    }

}

fun main() = runBlocking {
    foo()
}
