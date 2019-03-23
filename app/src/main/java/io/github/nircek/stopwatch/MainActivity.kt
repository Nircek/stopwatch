package io.github.nircek.stopwatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.AppCompatImageButton

class MainActivity : AppCompatActivity() {
    var addNewStopwatch: AppCompatImageButton? = null
    fun addStopwatch() = Stopwatch().let {
        supportFragmentManager.beginTransaction().add(R.id.stopwatches, it, it.fragID).commit()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addNewStopwatch = findViewById(R.id.add_new_stopwatch)
        addNewStopwatch?.setOnClickListener { addStopwatch() }
        addStopwatch()
    }
}
