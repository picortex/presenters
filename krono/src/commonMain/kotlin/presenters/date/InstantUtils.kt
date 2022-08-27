package presenters.date

import kotlinx.datetime.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun Instant.last(days: Int) : Instant = this - days.days