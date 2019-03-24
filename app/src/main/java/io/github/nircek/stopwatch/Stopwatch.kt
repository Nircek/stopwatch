package io.github.nircek.stopwatch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatImageButton
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import java.util.*
import kotlin.concurrent.fixedRateTimer

class Stopwatch : Fragment() {

    companion object {
        private var last_id = 0
        private fun getNextID() = ++last_id
    }

    private var timer: Timer? = null
    private var t: TextView? = null
    private var l: Button? = null
    private var r: Button? = null
    private var d: AppCompatImageButton? = null
    private var time: Long = 0
    private var started: Boolean = false
    val fragID = "frag" + getNextID().toString()

    private fun update() {
        var d = time
        if (started)
            d = (System.currentTimeMillis() - d)
        d /= 1000
        val c = d%60
        d /= 60
        val b = d%60
        d /= 60
        activity?.let { it.runOnUiThread {
            t?.text = it.applicationContext.getString(R.string.clock, d, b, c)
            l?.text = it.applicationContext.getString(if (started) R.string.stop else R.string.start)
        } }

    }

    private fun tStart() {
        timer?.cancel()
        timer = fixedRateTimer("default", true, 0, 200) { update() }
    }

    private fun tStop() {
        timer?.cancel()
        timer = null
    }

    private fun start() {
        if (!started)
            time = System.currentTimeMillis() - time
        started = true
        tStart()
    }

    private fun stop() {
        tStop()
        if (started)
            time = System.currentTimeMillis() - time
        started = false
        update()
    }

    private fun reset() {
        tStop()
        time = 0
        started = false
        update()
    }

    private fun destroy() {
        activity?.let {
            val tag = it.supportFragmentManager.findFragmentByTag(fragID)
            if (tag != null) {
                timer?.cancel()
                it.supportFragmentManager.beginTransaction().remove(tag).commitAllowingStateLoss()
            } else Log.e(activity.toString(), it.applicationContext.getString(R.string.not_existing_destroyed, fragID))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stopwatch, container, false)
    }

    override fun onStart() {
        super.onStart()
        t = view?.findViewById(R.id.clock)
        l = view?.findViewById(R.id.left)
        r = view?.findViewById(R.id.right)
        d = view?.findViewById(R.id.destroy)
        l?.setOnClickListener { if (started) stop() else start() }
        r?.setOnClickListener { reset() }
        d?.setOnClickListener { destroy() }
        update()
    }
}
