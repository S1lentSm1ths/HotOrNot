package com.vsc.hotornot

import android.content.Intent
import android.graphics.Paint
import android.graphics.drawable.Drawable
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
import com.vsc.hotornot.repository.FriendRepository
import com.vsc.hotornot.repository.UserRepository
import kotlin.math.min

private const val START_OF_FOR_LOOP = 0
private const val MIN_CHIPS_COUNT = 1

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var userRepository: UserRepository
    private lateinit var friendRepository: FriendRepository
    private lateinit var user: User
    private var listOfSavedFriends: List<Friend>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        getRepositoriesInstance()
        user = userRepository.getUser()!!
        listOfSavedFriends = friendRepository.getFriends()
        setRandomPerson()
        setEmail()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        changePersonOnClick()
        onEmailClicked()
    }

    private fun getRepositoriesInstance() {
        userRepository = UserRepository.getInstance(this.context)
        friendRepository = FriendRepository.getInstance(requireContext())
    }

    private fun setRandomPerson() {
        val randomFriendNumber = (START_OF_FOR_LOOP until listOfSavedFriends!!.size).random()
        if (listOfSavedFriends != null) {
            displayFriend(randomFriendNumber)
        }
    }

    private fun displayFriend(friendPosition: Int) {
        val currentFriend = listOfSavedFriends?.get(friendPosition)
        if (currentFriend != null) {
            setPersonNameAndImage(currentFriend.image, currentFriend.firstName)
            removeChips()
            displayFriendCharacteristics(currentFriend.characteristics)
        }
    }

    private fun changePersonOnClick() {

        binding.personHotButton.setOnClickListener {
            setRandomPerson()
        }
        binding.personNotButton.setOnClickListener {
            setRandomPerson()
        }
    }

    private fun setPersonNameAndImage(drawableImage: Int, friendName: String) {
        binding.personImage.setImageResource(drawableImage)
        binding.personName.text = friendName
        hideButtonOnNameChanged(friendName)
    }

    private fun hideButtonOnNameChanged(friendName: String) {
        if (friendName == resources.getString(R.string.second_person_name)) {
            binding.personNotButton.visibility = View.GONE
            binding.personHotButton.visibility = View.VISIBLE
        } else if (friendName == resources.getString(R.string.third_person_name)) {
            binding.personHotButton.visibility = View.GONE
            binding.personNotButton.visibility = View.VISIBLE
        } else if (friendName == resources.getString(R.string.first_person_name)) {
            binding.personNotButton.visibility = View.VISIBLE
            binding.personHotButton.visibility = View.VISIBLE
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

    private fun displayFriendCharacteristics(friendCharacteristics: List<String>?) {
        if (friendCharacteristics != null) {
            for (i in MIN_CHIPS_COUNT until friendCharacteristics.size) {
                val chip = Chip(activity)
                chip.text = (friendCharacteristics[i])
                binding.chipGroup.addView(chip)
            }
        }
    }

    private fun removeChips() =
        binding.chipGroup.removeAllViews()
}