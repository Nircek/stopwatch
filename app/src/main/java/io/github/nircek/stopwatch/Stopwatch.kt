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
    private var time: Long? = null
    private var started: Boolean = false
    val fragID = "frag" + getNextID().toString()
    private fun update() {
        if (started) {
            var d = (System.currentTimeMillis() - time!!)
            d /= 1000
            val c = d%60
            d /= 60
            val b = d%60
            d /= 60
            activity?.runOnUiThread {
                t?.text = activity!!.applicationContext.getString(R.string.clock, d, b, c)
            }
        }
    }
    private fun start() {
        started = true
        time = System.currentTimeMillis() - if (time != null) time!! else 0L
        l?.setOnClickListener { stop() }
        l?.text = activity!!.applicationContext.getString(R.string.stop)
        if(timer == null) {
            timer = fixedRateTimer("default", false, 0, 200) {
                update()
            }
        }
    }
    private fun stop() {
        time = if (time != null) System.currentTimeMillis() - time!! else 0L
        started = false
        timer?.cancel()
        timer = null
        l?.setOnClickListener{ start() }
        l?.text = activity!!.applicationContext.getString(R.string.start)
    }
    private fun reset() {
        stop()
        time = null
        t?.text = activity!!.applicationContext.getString(R.string.clock, 0, 0, 0)
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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stopwatch, container, false)
    }

    override fun onStart() {
        super.onStart()
        t = view?.findViewById(R.id.clock)
        l = view?.findViewById(R.id.left)
        r = view?.findViewById(R.id.right)
        d = view?.findViewById(R.id.destroy)
        l?.setOnClickListener { start() }
        r?.setOnClickListener { reset() }
        d?.setOnClickListener { destroy() }
        start()
    }
}
