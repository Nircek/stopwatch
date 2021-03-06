package io.github.nircek.stopwatch

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.util.Log

class MainActivity : AppCompatActivity() {
    private var addNewStopwatch: ImageButton? = null
    private var sws: Stopwatches? = null
    private var sp: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        setContentView(R.layout.activity_main)
        sws = Stopwatches(supportFragmentManager, R.id.stopwatches)
        sp = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        addNewStopwatch = findViewById(R.id.add_new_stopwatch)
        try {
            sp?.getString("Stopwatches", "false,0")?.let { sws?.fromString(it) }
        } catch (e: Throwable) {
            Log.e(this.toString(), e.toString());
            sws?.new()
        }
        addNewStopwatch?.setOnClickListener { sws?.new() }
    }

    override fun onPause() {
        super.onPause()
        sp?.edit()?.putString("Stopwatches", sws?.toString())?.apply()
    }
}
