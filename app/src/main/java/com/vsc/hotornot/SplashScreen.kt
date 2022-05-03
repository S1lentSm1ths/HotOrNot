package com.vsc.hotornot

import android.content.Context
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
    private val userSharedPreferences = UserSharedPreferences(this.context)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        checkIfUserExist(userSharedPreferences.getUserFirstName())
        return binding.root
    }

//    private fun getUserFirstName(): String? {
//        val userSharedPreferences = activity?.getSharedPreferences(
//            "userSharedPreferences",
//            Context.MODE_PRIVATE
//        )
//        return userSharedPreferences?.getString("first_name", null)
//    }

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