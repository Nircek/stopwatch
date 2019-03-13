package io.github.nircek.stopwatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.*
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity() {
    private var timer: Timer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val t = findViewById<TextView>(R.id.clock)
        findViewById<Button>(R.id.left).setOnClickListener {
            timer = fixedRateTimer("default", false, 0L, 1000){
                val x = t.text.split(":")
                var c = x[2].toInt()+1
                var b = x[1].toInt()
                if (c>59) {
                    b += c/60
                    c %= 60
                }
                var a = x[0].toInt()
                if (b>59) {
                    a += b/60
                    b %= 60
                }
                runOnUiThread {
                    t.text = applicationContext.getString(R.string.clock, a, b, c)
                }
            }
        }
    }
}
