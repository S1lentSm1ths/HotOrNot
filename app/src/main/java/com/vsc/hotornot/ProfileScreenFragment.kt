package com.vsc.hotornot

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vsc.hotornot.databinding.FragmentProfileScreenBinding
import com.vsc.hotornot.model.Gender
import com.vsc.hotornot.model.User

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

    private fun goBackToMainScreen() =
        findNavController().navigate(R.id.actionProfileScreenToMainScreen)

    private fun onPersonImageClicked() {
        binding.selectImageButton.setOnClickListener {
            imageChooser()
        }
    }

    private fun setUserInfo() {
        (user.firstName + getString(R.string.white_space) + user.lastName).also {
            binding.firstAndLastName.text = it
        }
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
        val galleryIntent = Intent()
        galleryIntent.type = resources.getString(R.string.gallery_type)
        galleryIntent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(
            Intent.createChooser(
                galleryIntent,
                resources.getString(R.string.gallery_select_image_text)
            )
        )
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            if (null != selectedImageUri) {
                binding.personImage.setImageURI(selectedImageUri)
            }
        }
}