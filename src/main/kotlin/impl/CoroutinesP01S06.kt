package impl

import kotlinx.coroutines.*
import service.ServiceP01S06
import kotlin.coroutines.startCoroutine

/**
 * Часть 1. Задание 6. Диспетчеры корутин.
 *
 * Необходимо внести изменения в логику функции `execute()` таким образом,
 * чтобы `log.logAfter()` вызывался с тем же контекстом, который был установлен внутри `body()`
 */
object CoroutinesP01S06 {
    suspend fun execute(
        log: ServiceP01S06.Log,
        body: suspend () -> Unit
    ) = withContext(Dispatchers.Unconfined) {
        log.logBefore()
        body()
        log.logAfter()
    }
}
