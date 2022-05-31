package com.vsc.hotornot

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.vsc.hotornot.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var userInteractionTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onUserInteraction() {
        userInteractionTime = System.currentTimeMillis()
        super.onUserInteraction()
        Log.i("appname", "Interaction")
    }

    override fun onUserLeaveHint() {
        val uiDelta = System.currentTimeMillis() - userInteractionTime
        super.onUserLeaveHint()
        Log.i("bThere", "Last User Interaction = $uiDelta")
        if (uiDelta < 100) Log.i("appname", "Home Key Pressed") else Log.i(
            "appname",
            "We are leaving, but will probably be back shortly!"
        )
    }
}