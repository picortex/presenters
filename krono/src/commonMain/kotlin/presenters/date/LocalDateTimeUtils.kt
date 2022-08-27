package presenters.date

import kotlinx.datetime.LocalDateTime

fun LocalDateTime.format(formatter: String) = DateFormatter(formatter).format(this)