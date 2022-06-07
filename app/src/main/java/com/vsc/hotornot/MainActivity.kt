package com.vsc.hotornot

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.vsc.hotornot.databinding.ActivityMainBinding
import java.util.*

private const val MAX_INACTIVE_SECONDS = 600000L

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var onPauseTime = 0L
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setActionBarColor()
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        when (item.itemId) {
            R.id.optionsProfileScreen -> navController.navigate(R.id.profileScreen)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        onPauseTime = System.currentTimeMillis()
    }

    override fun onResume() {
        super.onResume()
        checkInactiveTime()
    }

    private fun setActionBarColor() {
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.main_screen_button_not_background
                )
            )
        )
    }

    private fun checkInactiveTime() {
        val currentTime = System.currentTimeMillis()
        if ((currentTime - onPauseTime) > MAX_INACTIVE_SECONDS) {
            navigateToMotivationScreen()
        }
    }

    private fun navigateToMotivationScreen() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val currentScreen = navController.currentDestination?.id
        if (currentScreen != R.id.registrationScreenFragment && currentScreen != R.id.splashScreen)
            navController.navigate(R.id.motivationScreenFragment)
    }
}