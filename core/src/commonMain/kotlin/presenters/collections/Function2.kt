package presenters.collections

fun interface Function2<in I1, in I2, out R> {
    fun invoke(input1: I1, input2: I2): R
}