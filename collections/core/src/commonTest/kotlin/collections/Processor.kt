package collections

data class Processor(val name: String, val cores: Int, val clock: Double)

val processors = listOf(
    Processor("Intel core i5", 4, 2.5),
    Processor("AMD T Ryzen 6", 4, 3.0),
    Processor("AMD U Ryzen 4", 2, 3.4),
    Processor("Intel Xeon 03", 8, 4.3)
)