package magic

import live.MutableLive
import live.mutableLiveOf
import kotlin.test.Test

class LiveMagic {

    fun notifyChange(new: Int) {
        println("Changed to $new")
    }

    @Test
    fun luge_magic() {
        val x: MutableLive<Int> = mutableLiveOf(5)
        x.watch { notifyChange(it) }

        x.value = 6

        // lkdjlskjdlksjd
        x.value = 7

        // dlkjalkdsa
        x.value = 8
    }
}