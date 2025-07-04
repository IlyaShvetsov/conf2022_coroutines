package impl

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import service.ServiceP01S02

/**
 * Часть 1. Задание 2. Параллельное получение результатов (async).
 *
 * Вам нужно прочитать из репозитория (базы данных) все строки.
 * СУБД оптимизирована под параллельное чтение, но чтение каждой записи занимает некоторое время.
 * Используйте свойство `repository.rowCount` для получения количества записей в репозитории.
 * Используйте метод `repository.read()` для получения записи по индексу.
 */
class CoroutinesP01S02(
    private val repository: ServiceP01S02.Repository
) {
//    fun readFromRepository(): List<String> = runBlocking {
//        val list = arrayListOf<Deferred<String>>()
//        for (index in 0 until repository.rowCount) {
//            list.add( async { repository.read(index) } )
//        }
//        return@runBlocking list.awaitAll()
//    }

    fun readFromRepository(): List<String> = runBlocking {
        return@runBlocking (0 until repository.rowCount)
            .map { async { repository.read(it) } }
            .awaitAll()
    }
}
