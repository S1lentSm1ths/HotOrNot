package com.vsc.hotornot.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.text.toLowerCase
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vsc.hotornot.Constants.TRANSACTION_DURATION_TIME
import com.vsc.hotornot.R
import com.vsc.hotornot.databinding.FragmentSplashScreenBinding
import com.vsc.hotornot.repository.FriendRepository
import com.vsc.hotornot.repository.UserRepository
import java.util.*

class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding
    private lateinit var userRepository: UserRepository
    private lateinit var friendRepository: FriendRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        getRepositoriesInstance()
        changeAppLogo()
        friendRepository.checkIfFriendsSaved()
        postTransactionDelayToNextScreen()
        return binding.root
    }

    private fun getRepositoriesInstance() {
        friendRepository = FriendRepository.getInstance(requireContext())
        userRepository = UserRepository.getInstance(this.context)
    }

    private fun postTransactionDelayToNextScreen() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                when (userRepository.checkIfUserExist()) {
                    true -> navigateToMainScreen()
                    false -> navigateToRegistrationScreen()
                }
            }, TRANSACTION_DURATION_TIME)
    }

    private fun navigateToMainScreen() =
        findNavController().navigate(R.id.actionSplashScreenToMainScreen)

    private fun navigateToRegistrationScreen() =
        findNavController().navigate(R.id.actionSplashScreenToRegistrationScreenFragment)

    private fun changeAppLogo() {
        when (isEnglishLanguage()) {
            true -> binding.appLogo.setImageResource(R.drawable.app_logo)
            else -> binding.appLogo.setImageResource(R.drawable.bulgarian_app_logo)
        }
    }

    private fun isEnglishLanguage(): Boolean {
        return when (Locale.getDefault().language.lowercase()) {
            resources.getString(R.string.language_english) -> true
            else -> false
        }
    }
}