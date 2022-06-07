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
import com.vsc.hotornot.repository.FriendRepository
import com.vsc.hotornot.repository.UserRepository

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
        friendRepository.checkIfFriendsSaved()
        postTransactionDelayToNextScreen()
        return binding.root
    }

    private fun getRepositoriesInstance() {
        friendRepository = FriendRepository.getInstance(this.context, resources)
        userRepository = UserRepository.getInstance(this.context)
    }

    private fun postTransactionDelayToNextScreen() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                userRepository.checkIfUserExist(
                    navigateToMainScreen(),
                    navigateToRegistrationScreen()
                )
            }, TRANSACTION_DURATION_TIME)
    }

    private fun navigateToMainScreen() =
        findNavController().navigate(R.id.actionSplashScreenToMainScreen)

    private fun navigateToRegistrationScreen() =
        findNavController().navigate(R.id.actionSplashScreenToRegistrationScreenFragment)
}