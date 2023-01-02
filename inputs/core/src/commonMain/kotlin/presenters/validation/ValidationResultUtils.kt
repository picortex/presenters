package presenters.validation

internal fun ValidationResult.throwIfInvalid() {
    if (this is Invalid) throw cause
}