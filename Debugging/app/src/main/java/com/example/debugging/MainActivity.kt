package com.example.debugging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Log.d(TAG, "this is where the app crashed before")
//        val divisionTextView: TextView = findViewById(R.id.division_textview)
//        Log.d(TAG, "this should be logged if the bug is fixed")
//        divisionTextView.text = "Hello, debugging!"
        setContentView(R.layout.activity_main)
        Log.d(TAG, "this is where the app crashed before")
        val divisionTextView: TextView = findViewById(R.id.division_textview)
        Log.d(TAG, "this should be logged if the bug is fixed")
        val thread = Thread {
            Thread.sleep(3000)
            runOnUiThread {
                divisionTextView.text = "Hello, debugging!"
            }
            logging()
            division()
        }
        thread.start()
    }

    fun logging() {
        Log.e(TAG, "ERROR: a serious error like an app crash")
        Log.w(TAG, "WARN: warns about the potential for serious errors")
        Log.i(TAG, "INFO: reporting technical information, such as an operation succeeding")
        Log.d(TAG, "DEBUG: reporting technical information useful for debugging")
        Log.v(TAG, "VERBOSE: more verbose than DEBUG logs")
    }

    fun division() {
        val numerator = 60
        var denominator = 4
        // repeat(5) {
        repeat(4) {
            Thread.sleep(3000)
            runOnUiThread {
                Log.d(TAG, "$denominator")
                val result = numerator / denominator
                Log.v(TAG, "${result}")
                findViewById<TextView>(R.id.division_textview).setText("${result}")
                denominator--
            }
        }
    }
}