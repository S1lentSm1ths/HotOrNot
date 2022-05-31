package com.vsc.hotornot

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vsc.hotornot.databinding.FragmentMainScreenBinding
import com.vsc.hotornot.model.User

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
        setRandomPerson()
        setEmail()
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

    private fun setRandomPerson() {
        val friends = userSharedPreferences.getFriends()
        val minRandomNumber = 0
        if (friends != null) {
            when ((minRandomNumber..friends.size).random()) {
                0 -> setFirstPerson()
                1 -> setSecondPerson()
                2 -> setThirdPerson()
            }
        }
    }

    private fun setFirstPerson() {
        setPersonNameAndImage(R.drawable.first_person_img, R.string.first_person_name)
    }

    private fun setSecondPerson() {
        setPersonNameAndImage(R.drawable.second_person_img, R.string.second_person_name)
    }

    private fun setThirdPerson() {
        setPersonNameAndImage(R.drawable.third_person_img, R.string.third_person_name)
    }

    private fun changePersonImageOnClick() {

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
        if (personNameText == R.string.second_person_name.toString()) {
            binding.personNotButton.visibility = View.GONE
            binding.personHotButton.visibility = View.VISIBLE
        } else if (personNameText == R.string.third_person_name.toString()) {
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

    private fun onButtonProfileScreenClicked() {
        binding.profileScreenMenu.setOnClickListener {
            navigateToProfileScreen()
        }
    }

    private fun navigateToProfileScreen() {
        findNavController().navigate(R.id.actionMainScreenToProfileScreen)
    }
}