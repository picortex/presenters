package forms

import presenters.forms.*
import viewmodel.ViewModelConfig

class TestForm<F : Fields>(
    override val fields: F,
    builder: FormActionsBuildingBlock<Map<String, String>>
) : Form<F, Map<String, String>>(
    heading = "Test Form",
    details = "This is a form for testing",
    fields = fields,
    config = ViewModelConfig().toFormConfig(),
    builder
)