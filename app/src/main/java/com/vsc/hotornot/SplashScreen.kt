package com.vsc.hotornot

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        postTransactionDelay()
        return binding.root
    }

    private fun postTransactionDelay() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                actionGoToMainScreen()
            }, transactionDurationTime)
    }

    private fun actionGoToMainScreen(){
        findNavController().navigate(R.id.actionSplashScreenToMainScreen)
    }
}