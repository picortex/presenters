package presenters

fun Fields.button(
    label: String,
    name: String = label,
) = getOrCreate(name) { ButtonInputField(name, Label(label, false)) }