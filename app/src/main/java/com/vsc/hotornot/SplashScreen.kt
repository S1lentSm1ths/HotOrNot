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
import com.vsc.hotornot.databinding.FragmentSplashScreenBinding

class SplashScreen : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        getSharedData()
        return binding.root
    }

    private fun getSharedData(){
        val userSharedPreferences = activity?.getSharedPreferences(getString(R.string.user_shared_preferences_key), Context.MODE_PRIVATE)
        val firstName = userSharedPreferences?.getString("name$", null)
        val lastName = userSharedPreferences?.getString("last_name", null)

        checkIfUserExist(firstName)
    }

    private fun checkIfUserExist(firstName: String?){
        if (firstName != null){
            postTransactionDelayToMainScreen()
        }
        else {
            postTransactionDelayToRegistrationScreen()
        }
    }

    private fun postTransactionDelayToMainScreen() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                findNavController().navigate(R.id.actionSplashScreenToMainScreen)
            }, 2500)
    }

    private fun postTransactionDelayToRegistrationScreen() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                findNavController().navigate(R.id.actionSplashScreenToRegistrationScreenFragment)
            }, 1500)
    }
}