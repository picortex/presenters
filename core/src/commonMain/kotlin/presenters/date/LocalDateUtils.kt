package presenters.date

import kotlinx.datetime.LocalDate

fun LocalDate.format(formatter: String) = DateFormatter(formatter).format(this)