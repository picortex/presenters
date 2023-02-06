package presenters.internal

import kash.MoneyFormatter


@PublishedApi
internal val DEFAULT_FORMATTER = MoneyFormatter(abbreviate = false)