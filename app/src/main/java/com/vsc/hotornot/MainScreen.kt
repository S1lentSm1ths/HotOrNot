package com.vsc.hotornot

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vsc.hotornot.databinding.FragmentMainScreenBinding
import com.vsc.hotornot.model.User

class MainScreen : Fragment() {

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        changePersonImageOnClick()
        onEmailClicked()
        onButtonProfileScreenClicked()
    }

    private fun getUserSharedPreferencesInstance() {
        userSharedPreferences = UserSharedPreferences(activity)
    }

    private fun changePersonImageOnClick() {
        val personImage = binding.personImage
        binding.personHotButton.setOnClickListener {
            personImage.setImageResource(R.drawable.second_person_img)
            binding.personName.text = getString(R.string.name_stan)
            checkPersonName()
        }
        binding.personNotButton.setOnClickListener {
            personImage.setImageResource(R.drawable.third_person_img)
            binding.personName.text = getString(R.string.name_georgi)
            checkPersonName()
        }
    }

    private fun checkPersonName() {
        val personNameText = binding.personName.text
        if (personNameText == "Georgi") {
            binding.personNotButton.visibility = View.GONE
            binding.personHotButton.visibility = View.VISIBLE
        } else if (personNameText == "Stan") {
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
        val emailSubject = "Android dev"
        val sayHello = "zdr bepce ko pr"
        val sendEmailText = "$userFirstName $userLastName $sayHello"
        val chooseApp = "Choose app"
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(userEmail))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, sendEmailText)
        startActivity(Intent.createChooser(emailIntent, chooseApp))
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

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.main_screen_options_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.overflowMenu){
////            findNavController().navigate(R.id.actionMainScreenToProfileScreen)
//            Toast.makeText(this.context, "Clicked", Toast.LENGTH_LONG).show()
//        }
//        return true
//    }
}