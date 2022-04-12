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

class SplashScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_splash_screen, container, false)

        postTransactionDelay()
        return view
    }

    private fun checkIfUserExist(){
        val userSharedPreferences = activity?.getSharedPreferences(getString(R.string.user_shared_preferences_key), Context.MODE_PRIVATE)
        val namePosition = userSharedPreferences?.getInt("name_position", 0)
        val firstName = userSharedPreferences?.getString("name$", null)
        val lastName = userSharedPreferences?.getString("last_name", null)
    }

    private fun postTransactionDelay() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                findNavController().navigate(R.id.actionSplashScreenToMainScreen)
            }, 2500)
    }
}