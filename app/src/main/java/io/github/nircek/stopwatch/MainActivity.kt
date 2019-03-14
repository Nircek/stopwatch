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
    private fun start() {
        if(timer == null) {
            timer = fixedRateTimer("default", false, 1000, 1000) {
                // TODO: instead of increment counter every second, try to calculate (subtract) new value from the actual time
                t?.let {
                    val x = it.text.split(":")
                    var c = x[2].toInt() + 1
                    var b = x[1].toInt()
                    if (c > 59) {
                        b += c / 60
                        c %= 60
                    }
                    var a = x[0].toInt()
                    if (b > 59) {
                        a += b / 60
                        b %= 60
                    }
                    runOnUiThread {
                        it.text = applicationContext.getString(R.string.clock, a, b, c)
                    }
                }
            }
            l?.setOnClickListener { stop() }
            l?.text = applicationContext.getString(R.string.stop)
        }
    }
    private fun stop() {
        timer?.cancel()
        timer = null
        l?.setOnClickListener{ start() }
        l?.text = applicationContext.getString(R.string.start)
    }
    private fun reset() {
        stop()
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
