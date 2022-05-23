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
import com.vsc.hotornot.model.User
import java.util.Collections.shuffle

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var userSharedPreferences: UserSharedPreferences
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        getUserSharedPreferencesInstance()
        user = userSharedPreferences.getUserData()!!
        setEmail()
        generateListOfFriends()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        changePersonImageOnClick()
        onEmailClicked()
        onButtonProfileScreenClicked()
    }

    private fun getUserSharedPreferencesInstance() {
        userSharedPreferences = UserSharedPreferences.getInstance(this.context)
    }

    private fun changePersonImageOnClick() {

        binding.personHotButton.setOnClickListener {
            changePersonNameAndImage(R.drawable.second_person_img, R.string.name_stan)
        }
        binding.personNotButton.setOnClickListener {
            changePersonNameAndImage(R.drawable.third_person_img, R.string.name_georgi)
        }
    }

    private fun changePersonNameAndImage(drawableImage: Int, personName: Int) {
        val personImage = binding.personImage
        personImage.setImageResource(drawableImage)
        binding.personName.text = getString(personName)
        checkPersonName()
    }

    private fun checkPersonName() {
        val personNameText = binding.personName.text
        if (personNameText == R.string.name_georgi.toString()) {
            binding.personNotButton.visibility = View.GONE
            binding.personHotButton.visibility = View.VISIBLE
        } else if (personNameText == R.string.name_stan.toString()) {
            binding.personHotButton.visibility = View.GONE
            binding.personNotButton.visibility = View.VISIBLE
        }
    }

    private fun setEmail() {
        binding.userEmail.hint = user.email
        binding.userEmail.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    private fun sendEmail() {
        val userFirstName = user.firstName
        val userLastName = user.lastName
        val userEmail = user.email
        val emailSubject = getStringFromResources(R.string.email_subject)
        val sayHello = getStringFromResources(R.string.email_say_hello)
        val sendEmailText = "$userFirstName $userLastName $sayHello"
        val chooseApp = getStringFromResources(R.string.email_chooser_text)
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = getStringFromResources(R.string.email_type)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(userEmail))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, sendEmailText)
        startActivity(Intent.createChooser(emailIntent, chooseApp))
    }

    private fun getStringFromResources(resource: Int): String {
        return resources.getString(resource)
    }

    private fun onEmailClicked() {
        binding.userEmail.setOnClickListener {
            sendEmail()
        }
    }

    private fun onButtonProfileScreenClicked() {
        binding.profileScreenMenu.setOnClickListener {
            navigateToProfileScreen()
        }
    }

    private fun navigateToProfileScreen() {
        findNavController().navigate(R.id.actionMainScreenToProfileScreen)
    }

    private fun generateListOfFriends() {
        val listOfFiends = resources.getStringArray(R.array.characteristics).toList()
        shuffle(listOfFiends)
        val randomCharacteristics = (0..listOfFiends.size).random()
        createChips(randomCharacteristics, listOfFiends)
    }

    private fun createChips(chips: Int, friendsCharacteristics: List<String>) {
        for (i in 0..chips) {
            val chip = Chip(activity)
            chip.text = (friendsCharacteristics[i])
            binding.chipGroup.addView(chip)
        }
    }
}