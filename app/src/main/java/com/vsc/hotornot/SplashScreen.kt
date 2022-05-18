package com.vsc.hotornot

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vsc.hotornot.Constants.TRANSACTION_DURATION_TIME
import com.vsc.hotornot.databinding.FragmentSplashScreenBinding

class SplashScreen : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding
    private lateinit var userSharedPreferences: UserSharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        getUserSharedPreferencesInstance()
        checkIfUserExist(userSharedPreferences.getUserData())
        return binding.root
    }

    private fun getUserSharedPreferencesInstance() {
        userSharedPreferences = UserSharedPreferences(activity)
        userSharedPreferences.deleteUser()
    }

    private fun checkIfUserExist(user: User?) {
        if (user != null) {
            postTransactionDelayToMainScreen()
        } else {
            postTransactionDelayToRegistrationScreen()
        }
    }

    private fun postTransactionDelayToMainScreen() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                navigateToMainScreen()
            }, TRANSACTION_DURATION_TIME)
    }

    private fun navigateToMainScreen() {
        findNavController().navigate(R.id.actionSplashScreenToMainScreen)
    }

    private fun postTransactionDelayToRegistrationScreen() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                navigateToRegistrationScreen()
            }, TRANSACTION_DURATION_TIME)
    }

    private fun navigateToRegistrationScreen() {
        findNavController().navigate(R.id.actionSplashScreenToRegistrationScreenFragment)
    }
}