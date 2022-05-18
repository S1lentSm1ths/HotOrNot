package com.vsc.hotornot

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vsc.hotornot.databinding.FragmentProfileScreenBinding
import com.vsc.hotornot.model.User

private const val SELECT_PICTURE = 200

class ProfileScreen : Fragment() {

    private lateinit var binding: FragmentProfileScreenBinding
    private lateinit var userSharedPreferences: UserSharedPreferences
    private lateinit var user: User
    private val man = "Man"
    private val woman = "Woman"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileScreenBinding.inflate(layoutInflater, container, false)
        getUserSharedPreferencesInstance()
        setUserInfo()
        onArrowBackClicked()
        onPersonImageClicked()
        return binding.root
    }

    private fun getUserSharedPreferencesInstance() {
        userSharedPreferences = UserSharedPreferences(activity)
        user = userSharedPreferences.getUserData()!!
    }

    private fun onArrowBackClicked() {
        binding.toolbarMenu.setNavigationOnClickListener {
            goBackToMainScreen()
        }
    }

    private fun goBackToMainScreen() {
        findNavController().navigate(R.id.actionProfileScreenToMainScreen)
    }

    private fun onPersonImageClicked() {
        binding.selectImageButton.setOnClickListener {
            imageChooser()
        }
    }

    private fun setUserInfo() {
        binding.firstAndLastName.text = user.firstName + " " + user.lastName
        binding.userEmail.text = user.email
        checkUserSex()
    }

    private fun checkUserSex() {
        if (user.gender == man) {
            setMan()
        } else if (user.gender == woman) {
            setWoman()
        } else {
            setOther()
        }
    }

    private fun setMan() {
        binding.userSex.text = man
        binding.userSex.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_man, 0, 0, 0)
    }

    private fun setWoman() {
        binding.userSex.text = woman
        binding.userSex.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_woman, 0, 0, 0)
    }

    private fun setOther() {
        val other = "Other"
        binding.userSex.text = other
        binding.userSex.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_login_person, 0, 0, 0)
    }

    private fun imageChooser() {

        // create an instance of the
        // intent of the type image
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SELECT_PICTURE) {
            // Get the url of the image from data
            val selectedImageUri: Uri? = data?.data
            if (null != selectedImageUri) {
                // update the preview image in the layout
                binding.personImage.setImageURI(selectedImageUri)
            }
        }
    }
}