package io.github.nircek.stopwatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.widget.AppCompatImageButton
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {
    var stopwatches: LinearLayout? = null
    var addNewStopwatch: AppCompatImageButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stopwatches = findViewById(R.id.stopwatches)
        addNewStopwatch = findViewById(R.id.add_new_stopwatch)
        addNewStopwatch?.setOnClickListener {
            val s = Stopwatch()
            supportFragmentManager.beginTransaction().add(stopwatches!!.id, s, s.frag_id).commit()
        }
    }
}
