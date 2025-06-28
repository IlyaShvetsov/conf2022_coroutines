package impl

import kotlinx.coroutines.*

/**
 * Часть 2. Задание 3. Синхронизация доступа.
 *
 * А что делать, если вам требуется не просто увеличивать значение счетчика,
 * а выполнять более сложные преобразования общего ресурса - например, собирать строку?
 */
object CoroutinesP02S03 {
    suspend fun executeAndConcatenate(times: Int, body: suspend (i: Int) -> String): String =
        coroutineScope {
            (0 until times)
                .map { index -> async { body(index) } }
                .awaitAll()
                .sum()
        }
}

private fun Iterable<String>.sum(): String {
    val sum = StringBuilder()
    for (element in this) {
        sum.append(element)
    }
    return sum.toString()
}
