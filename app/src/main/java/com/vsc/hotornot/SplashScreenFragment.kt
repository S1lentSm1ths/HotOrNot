package com.vsc.hotornot

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.vsc.hotornot.Constants.TRANSACTION_DURATION_TIME
import com.vsc.hotornot.databinding.FragmentSplashScreenBinding
import com.vsc.hotornot.model.Friend
import com.vsc.hotornot.model.Gender
import com.vsc.hotornot.model.User
import java.util.*
import java.util.Collections.shuffle
import kotlin.collections.ArrayList

class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding
    private lateinit var userSharedPreferences: UserSharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        getUserSharedPreferencesInstance()
        saveFriendList()
        checkIfUserExist(userSharedPreferences.getUserData())
        return binding.root
    }

    private fun getUserSharedPreferencesInstance() {
        userSharedPreferences = UserSharedPreferences.getInstance(this.context)
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

    private fun saveFriendList() {
        val firstFriend = Friend(
            resources.getString(R.string.first_person_name),
            resources.getString(R.string.first_person_last_name),
            resources.getString(R.string.first_person_email),
            Gender.MAN,
            resources.getString(R.string.first_person_interests),
            resources.getString(R.string.first_person_rating),
            createFriendCharacteristics()
        )
        val secondFriend = Friend(
            resources.getString(R.string.second_person_name),
            resources.getString(R.string.second_person_last_name),
            resources.getString(R.string.second_person_email),
            Gender.MAN,
            resources.getString(R.string.second_person_interests),
            resources.getString(R.string.second_person_rating),
            createFriendCharacteristics()
        )
        val thirdFriend = Friend(
            resources.getString(R.string.third_person_name),
            resources.getString(R.string.third_person_last_name),
            resources.getString(R.string.third_person_email),
            Gender.MAN,
            resources.getString(R.string.third_person_interests),
            resources.getString(R.string.third_person_rating),
            createFriendCharacteristics()
        )
        val listOfFriends = listOf(firstFriend, secondFriend, thirdFriend)
        userSharedPreferences.setListOfFriends(listOfFriends)
    }

    private fun createFriendCharacteristics(): List<String> {
        val friendCharacteristics = resources.getStringArray(R.array.characteristics).toList()
        shuffle(friendCharacteristics)
        val minLoopNumber = 0
        val minCharacteristicsCount = 1
        val characteristicsCount = (minCharacteristicsCount..friendCharacteristics.size).random()
        val listOfCharacteristics = mutableListOf<String>()
        for (i in minLoopNumber..characteristicsCount) {
           listOfCharacteristics.add(friendCharacteristics[i])
        }
        return listOfCharacteristics
    }

//    private fun createChips(chips: Int, friendsCharacteristics: List<String>) {
//        val minLoopNumber = 0
//        for (i in minLoopNumber..chips) {
//            val chip = Chip(activity)
//            chip.text = (friendsCharacteristics[i])
//            binding.chipGroup.addView(chip)
//        }
//    }
}