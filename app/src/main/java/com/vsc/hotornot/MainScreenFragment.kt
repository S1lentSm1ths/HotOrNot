package com.vsc.hotornot

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.vsc.hotornot.databinding.FragmentMainScreenBinding
import com.vsc.hotornot.model.Friend
import com.vsc.hotornot.model.User
import kotlin.math.min

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var userSharedPreferences: UserSharedPreferences
    private lateinit var user: User
    private var friends: List<Friend>? = null
    private var randomNumberToSetFriend = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        getUserSharedPreferencesInstance()
        user = userSharedPreferences.getUserData()!!
        setRandomPerson()
        setEmail()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        changePersonOnClick()
        onEmailClicked()
        onButtonProfileScreenClicked()
    }

    private fun getUserSharedPreferencesInstance() {
        userSharedPreferences = UserSharedPreferences.getInstance(this.context)
        friends = userSharedPreferences.getFriends()
    }

    private fun setRandomPerson() {
        val minRandomNumber = 0
        val secondPersonNumber = 1
        val thirdPersonNumber = 2
        randomNumberToSetFriend = (minRandomNumber until friends!!.size).random()
        if (friends != null) {
            when (randomNumberToSetFriend) {
                0 -> setFirstPerson()
                1 -> setSecondPerson()
                2 -> setThirdPerson()
            }
        }
    }

    private fun setFirstPerson() {
        val firstPersonNumber = 0
        setPersonNameAndImage(R.drawable.first_person_img, R.string.first_person_name)
        removeChips()
        createChips(friends?.get(firstPersonNumber)?.characteristics)
    }

    private fun setSecondPerson() {
        val secondPersonNumber = 1
        setPersonNameAndImage(R.drawable.second_person_img, R.string.second_person_name)
        removeChips()
        createChips(friends?.get(secondPersonNumber)?.characteristics)
    }

    private fun setThirdPerson() {
        val thirdPersonNumber = 2
        setPersonNameAndImage(R.drawable.third_person_img, R.string.third_person_name)
        removeChips()
        createChips(friends?.get(thirdPersonNumber)?.characteristics)
    }

    private fun changePersonOnClick() {

        binding.personHotButton.setOnClickListener {
            setThirdPerson()
        }
        binding.personNotButton.setOnClickListener {
            setSecondPerson()
        }
    }

    private fun setPersonNameAndImage(drawableImage: Int, personName: Int) {
        val personImage = binding.personImage
        personImage.setImageResource(drawableImage)
        binding.personName.text = getString(personName)
        checkPersonName()
    }

    private fun checkPersonName() {
        val personNameText = binding.personName.text
        if (personNameText == resources.getString(R.string.second_person_name)) {
            binding.personNotButton.visibility = View.GONE
            binding.personHotButton.visibility = View.VISIBLE
        } else if (personNameText == resources.getString(R.string.third_person_name)) {
            binding.personHotButton.visibility = View.GONE
            binding.personNotButton.visibility = View.VISIBLE
        }
    }

    private fun setEmail() {
        binding.userEmail.hint = user.email
        binding.userEmail.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    private fun sendEmail() {
        val sendEmailText =
            "${user.firstName} ${user.lastName} ${resources.getString(R.string.email_say_hello)}"
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = resources.getString(R.string.email_type)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(user.email))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.email_subject))
        emailIntent.putExtra(Intent.EXTRA_TEXT, sendEmailText)
        startActivity(
            Intent.createChooser(
                emailIntent,
                resources.getString(R.string.email_chooser_text)
            )
        )
    }

    private fun onEmailClicked() {
        binding.userEmail.setOnClickListener {
            sendEmail()
        }
    }

    private fun createChips(friendCharacteristics: List<String>?) {
        val minChips = 1
        if (friendCharacteristics != null) {
            for (i in minChips until friendCharacteristics.size) {
                val chip = Chip(activity)
                chip.text = (friendCharacteristics[i])
                binding.chipGroup.addView(chip)
            }
        }
    }

    private fun removeChips() {
        binding.chipGroup.removeAllViews()
    }

    private fun onButtonProfileScreenClicked() {
        binding.profileScreenMenu.setOnClickListener {
            navigateToProfileScreen()
        }
    }

    private fun navigateToProfileScreen() {
        findNavController().navigate(R.id.actionMainScreenToProfileScreen)
    }
}