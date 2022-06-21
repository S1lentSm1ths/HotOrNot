package com.vsc.hotornot.view

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.vsc.hotornot.R
import com.vsc.hotornot.databinding.ActivityMainBinding

private const val MAX_INACTIVE_SECONDS = 60L

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var onPauseTime = 0L
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        setActionBarColor()
        removeActionBarTitle()
        showAndHideBackArrowOnDestinationChanged()
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun hideOptionsMenu() {
        when (navController.currentDestination?.id) {
            R.id.motivationScreenFragment -> R.menu.main_screen_options_menu
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.optionsProfileScreen -> findNavController(R.id.navHostFragment).navigate(R.id.profileScreen)
            android.R.id.home -> backToPreviousScreen()
        }
        when(navController.currentDestination?.id) {
            R.id.motivationScreenFragment -> item.isVisible = false
        }
        return super.onOptionsItemSelected(item)
    }

    private fun backToPreviousScreen() = navController.popBackStack()

    private fun showAndHideBackArrowOnDestinationChanged() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
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
        showAndHideBackArrowOnDestinationChanged()
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

    private fun removeActionBarTitle() = supportActionBar?.setDisplayShowTitleEnabled(false)

    private fun checkInactiveTime() {
        val currentTime = System.currentTimeMillis()
        if ((currentTime - onPauseTime) > MAX_INACTIVE_SECONDS) {
            navigateToMotivationScreen()
        }
    }

    private fun navigateToMotivationScreen() {
        navController = Navigation.findNavController(this, R.id.navHostFragment)
        val currentScreen = navController.currentDestination?.id
        if (currentScreen != R.id.registrationScreenFragment && currentScreen != R.id.splashScreen)
            navController.navigate(R.id.motivationScreenFragment)
    }
}