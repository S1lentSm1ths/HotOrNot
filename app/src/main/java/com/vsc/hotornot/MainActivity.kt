package com.vsc.hotornot

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.vsc.hotornot.databinding.ActivityMainBinding

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
        showAndHideBackArrowOnDestinationChanged()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        return super.onCreateView(name, context, attrs)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.optionsProfileScreen -> findNavController(R.id.nav_host_fragment).navigate(R.id.profileScreen)
            R.id.home -> backToPreviousScreen()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun backToPreviousScreen() = supportFragmentManager.popBackStack()

    private fun showAndHideBackArrowOnDestinationChanged() {
        NavController.OnDestinationChangedListener {_, destination, _ ->
            when (destination.id) {
                R.id.profileScreen -> supportActionBar?.setDisplayHomeAsUpEnabled(true)
                else -> supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
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