package scratch

import kotlinx.coroutines.*

private suspend fun fizz() {
    coroutineScope {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i ...")
                    delay(500L)
                }
            } catch (e: CancellationException) {
                println("job: I'm canceled")
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancel() // cancels the job
        job.join() // waits for job's completion
        println("main: Now I can quit.")
    }
}

private suspend fun bar() {
    while (true) {
    }
}

private suspend fun foo() {
    coroutineScope {
        val job = launch(Dispatchers.Default) {
            while (isActive) {
                try {
                    //bar()
                    yield()
                } catch (e: CancellationException) {
                    println("cancelled")
                }
            }
        }
        delay(1000L)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }
}

private suspend fun doc1() {
    coroutineScope {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5 && isActive) { // computation loop, just wastes CPU
                // print a message twice a second
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }
}

private suspend fun doc2() {
    coroutineScope {
        val job = launch(Dispatchers.Default) {
            repeat(5) { i ->
                try {
                    // print a message twice a second
                    println("job: I'm sleeping $i ...")
                    delay(500)
                } catch (e: Exception) {
                    // log the exception
                    println(e)
                }
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }
}

private suspend fun doc3() {
    coroutineScope {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i ...")
                    delay(500L)
                }
            } catch (e: Exception) {
                println(e)
            } finally {
                withContext(NonCancellable) { // try to remove it
                    println("job: I'm running finally")
                    try {
                        delay(1000L)
                        println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                    } catch (e: Exception) {
                        println("job: I CAN't")
                    }
                }
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }
}

fun main() = runBlocking {
    doc3()
}
