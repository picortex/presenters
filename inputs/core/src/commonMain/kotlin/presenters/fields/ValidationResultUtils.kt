package presenters.fields

internal fun ValidationResult.throwIfInvalid() {
    if (this is Invalid) throw cause
}