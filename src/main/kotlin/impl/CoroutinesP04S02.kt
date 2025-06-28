package impl

import kotlinx.coroutines.flow.*
import service.ServiceP04S02

/**
 * Часть 4. Задание 2. Преобразование асинхронных потоков.
 *
 * Вам предстоит преобразовать полученный список строк в список телефонов и "сохранить" каждый из них.
 * 1. Входная строка может содержать несколько телефонных номеров в произвольном формате, разделенных символами `,` или `;`.
 * 2. Каждый телефонный номер нужно нормализовать и сохранить (вызвать для него метод `writer.write()`).
 * 3. Сохранять нужно только мобильный телефон в формате `9xxxxxxxxx` (10 цифр).
 * 4. Параметр `limit` используется для ограничения количества получаемых телефонов.
 */
object CoroutinesP04S02 {
    suspend fun filterPhones(
        flow: Flow<String>,
        writer: ServiceP04S02.Writer,
        limit: Int
    ) {
        flow.transform { line -> line.split(',', ';').forEach { phone -> emit(phone)} }
            .map { phone -> phone.filter { c -> c.isDigit() }.takeLast(10) }
            .filter { phone -> phone.isNotBlank() && phone[0] == '9' }
            .take(limit)
            .collect { phone ->
                writer.write(phone)
            }
    }
}
