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
import com.vsc.hotornot.model.Gender
import com.vsc.hotornot.model.User

private const val RESULT_CODE = 200
private const val ICON_POSITION = 0

class ProfileScreen : Fragment() {

    private lateinit var binding: FragmentProfileScreenBinding
    private lateinit var userSharedPreferences: UserSharedPreferences
    private lateinit var user: User

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
        userSharedPreferences = UserSharedPreferences.getInstance(this.context)
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
        setGenderUi(user.gender)
    }

    private fun setGenderUi(gender: Gender) {
        binding.userSex.text = resources.getString(gender.genderText)
        binding.userSex.setCompoundDrawablesWithIntrinsicBounds(
            gender.genderIcon,
            ICON_POSITION,
            ICON_POSITION,
            ICON_POSITION
        )
    }

    private fun imageChooser() {

        // create an instance of the
        // intent of the type image
        val galleryIntent = Intent()
        galleryIntent.type = resources.getString(R.string.gallery_type)
        galleryIntent.action = Intent.ACTION_GET_CONTENT

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(
            Intent.createChooser(
                galleryIntent,
                resources.getString(R.string.gallery_select_image_text)
            ), RESULT_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RESULT_CODE) {
            // Get the url of the image from data
            val selectedImageUri: Uri? = data?.data
            if (null != selectedImageUri) {
                // update the preview image in the layout
                binding.personImage.setImageURI(selectedImageUri)
            }
        }
    }
}