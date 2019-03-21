package io.github.nircek.stopwatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.*
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity() {
    private var timer: Timer? = null
    private var t: TextView? = null
    private var l: Button? = null
    private var r: Button? = null
    private var time: Long? = null
    private var started: Boolean = false

    private fun update() {
        if (started) {
            var d = (System.currentTimeMillis() - time!!)
            d /= 1000
            val c = d%60
            d /= 60
            val b = d%60
            d /= 60
            runOnUiThread {
                t?.text = applicationContext.getString(R.string.clock, d, b, c)
            }
        }
    }
    private fun start() {
        started = true
        time = System.currentTimeMillis() - if (time != null) time!! else 0L
        l?.setOnClickListener { stop() }
        l?.text = applicationContext.getString(R.string.stop)
        if(timer == null) {
            timer = fixedRateTimer("default", false, 200, 200) {
                update()
            }
        }
    }
    private fun stop() {
        time = (System.currentTimeMillis() - time!!)
        started = true
        timer?.cancel()
        timer = null
        l?.setOnClickListener{ start() }
        l?.text = applicationContext.getString(R.string.start)
    }
    private fun reset() {
        stop()
        time = null
        t?.text = applicationContext.getString(R.string.clock, 0, 0, 0)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        t = findViewById(R.id.clock)
        l = findViewById(R.id.left)
        r = findViewById(R.id.right)
        l?.setOnClickListener{ start() }
        r?.setOnClickListener { reset() }
        start()
    }
}
