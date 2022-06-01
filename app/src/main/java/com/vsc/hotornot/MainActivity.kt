package com.vsc.hotornot

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.vsc.hotornot.databinding.ActivityMainBinding

private const val MAX_INACTIVE_SECONDS = 600000L

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var onPauseTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onPause() {
        super.onPause()
        onPauseTime = System.currentTimeMillis()
    }

    override fun onResume() {
        super.onResume()
        checkInactiveTime()
    }

    private fun checkInactiveTime() {
        val currentTime = System.currentTimeMillis()
        if ((currentTime - onPauseTime) > MAX_INACTIVE_SECONDS) {
            navigateToMotivationScreen()
        }
    }

    private fun navigateToMotivationScreen() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val currentScreen = navController.currentDestination?.id
        if (currentScreen != R.id.registrationScreenFragment && currentScreen != R.id.splashScreen)
            navController.navigate(R.id.motivationScreenFragment)
    }
}