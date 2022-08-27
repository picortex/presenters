package presenters.date

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import krono.PureDateFormatter
import krono.PureDateTimeFormatter

//typealias DateFormatter = datetime.DateFormatter

class DateFormatter(private val pattern: String) {
    fun format(date: LocalDate) = PureDateFormatter(pattern).formatDate(
        year = date.year,
        month = date.monthNumber,
        day = date.dayOfMonth
    )

    fun format(date: LocalDateTime) = PureDateTimeFormatter(pattern).formatDateTime(
        year = date.year,
        month = date.monthNumber,
        day = date.dayOfMonth,
        hour = date.hour,
        minutes = date.minute,
        seconds = date.second
    )
}