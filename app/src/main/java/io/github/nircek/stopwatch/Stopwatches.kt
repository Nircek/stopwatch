package io.github.nircek.stopwatch

import android.support.v4.app.FragmentManager

class Stopwatches (private val ft: FragmentManager, private val wh: Int) {
    private val list: MutableList<Stopwatch> = mutableListOf()
    fun add(x: Stopwatch) {
        x.parent = this
        list.add(x)
        ft.beginTransaction().add(wh, x, x.fragID).commit()
    }
    fun new() = add(Stopwatch())
    fun destroy(x: Stopwatch) {
        list.removeAll { it.fragID == x.fragID }
        x.parent = null
        x.destroy()
    }
    fun clear() {
        for (e in list)
            destroy(e)
    }
    override fun toString() = list.joinToString(";")
    fun fromString(l: String) {
        for (e in l.split(";"))
            add(Stopwatch().fromString(e))
    }
}
