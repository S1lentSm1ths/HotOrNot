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

class ProfileScreen : Fragment() {

    private lateinit var binding: FragmentProfileScreenBinding
    private lateinit var userSharedPreferences: UserSharedPreferences
    private lateinit var user: User
    private val man = Gender.MAN
    private val woman = Gender.WOMAN

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
        checkUserSex()
    }

    private fun checkUserSex() {
        if (user.gender == Gender.MAN) {
            setMan()
        } else if (user.gender == Gender.WOMAN) {
            setWoman()
        } else {
            setOther()
        }
    }

    private fun setMan() {
        binding.userSex.text = man.toString()
        binding.userSex.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_man, 0, 0, 0)
    }

    private fun setWoman() {
        binding.userSex.text = woman.toString()
        binding.userSex.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_woman, 0, 0, 0)
    }

    private fun setOther() {
        binding.userSex.text = R.string.other.toString()
        binding.userSex.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_login_person, 0, 0, 0)
    }

    private fun imageChooser() {

        // create an instance of the
        // intent of the type image
        val galleryIntent = Intent()
        galleryIntent.type = R.string.gallery_type.toString()
        galleryIntent.action = Intent.ACTION_GET_CONTENT

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(galleryIntent, R.string.gallery_select_image_text.toString()), RESULT_CODE)
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