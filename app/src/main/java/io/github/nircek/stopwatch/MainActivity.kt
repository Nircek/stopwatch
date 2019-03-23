package io.github.nircek.stopwatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
            //TODO: add a counter to the tag of new fragment
            fragmentManager.beginTransaction().add(stopwatches!!.id, Stopwatch(), "frag2").commit()
        }
    }
}
