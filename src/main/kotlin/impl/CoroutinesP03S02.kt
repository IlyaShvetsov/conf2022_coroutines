package impl

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

/**
 * Часть 3. Задание 2. Отправка элементов в канал.
 *
 * А теперь - наоборот, отправьте последовательно все элементы списка в канал.
 * Каждый элемент должен быть преобразован в строку с помощью функции `body()`.
 */
object CoroutinesP03S02 {
    fun CoroutineScope.listToChannel(
        list: List<Int>,
        body: suspend (Int) -> String
    ): ReceiveChannel<String> = produce {
        list.forEach { item ->
            send(body(item))
        }
    }
}
