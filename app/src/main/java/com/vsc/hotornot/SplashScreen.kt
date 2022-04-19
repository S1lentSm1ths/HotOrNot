package com.vsc.hotornot

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vsc.hotornot.Constants.Companion.transactionDurationTime
import com.vsc.hotornot.databinding.FragmentSplashScreenBinding

class SplashScreen : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding
    private val userSharedPreferences = UserSharedPreferences()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        userSharedPreferences.getUserData()
        val savedFirstName = userSharedPreferences.getUserData()
        checkIfUserExist()
        return binding.root
    }

    private fun checkIfUserExist(firstName: String?) {
        if (firstName != null) {
            postTransactionDelayToMainScreen()
        } else {
            postTransactionDelayToRegistrationScreen()
        }
    }

    private fun postTransactionDelayToMainScreen() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                findNavController().navigate(R.id.actionSplashScreenToMainScreen)
            }, transactionDurationTime)
    }

    private fun postTransactionDelayToRegistrationScreen() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                findNavController().navigate(R.id.actionSplashScreenToRegistrationScreenFragment)
            }, transactionDurationTime)
    }
}