package impl

import kotlinx.coroutines.*
import service.ServiceP02S01

/**
 * Часть 2. Задание 1. Синхронизация доступа.
 *
 * У вас есть класс `Counter`, который не является потокобезопасным.
 * Необходимо модифицировать функцию таким образом, чтобы вызовы `body(i)` остались параллельными,
 * и при этом `counter` возвращал бы корректный результат.
 */
object CoroutinesP02S01 {
    suspend fun executeAndSum(
        counter: ServiceP02S01.Counter,
        times: Int,
        body: suspend (i: Int) -> Int
    ) {
        coroutineScope {
            val list = arrayListOf<Deferred<Int>>()
            repeat(times) { i ->
                list.add( async { body(i) } )
            }
            list.awaitAll().forEach { i ->
                counter.add(i)
            }
        }
    }
}
