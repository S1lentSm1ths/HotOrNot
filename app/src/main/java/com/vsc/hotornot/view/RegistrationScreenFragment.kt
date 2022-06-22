package com.vsc.hotornot.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.vsc.hotornot.R
import com.vsc.hotornot.R.id.actionRegistrationScreenFragmentToMainScreen
import com.vsc.hotornot.databinding.FragmentRegistrationScreenBinding
import com.vsc.hotornot.model.Gender
import com.vsc.hotornot.model.User
import com.vsc.hotornot.repository.UserRepository
import java.util.*

private const val EMAIL_REQUIRED_SIGN = "@"
private const val THIRD_PROGRESS = 33

class RegistrationScreenFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationScreenBinding
    private lateinit var userRepository: UserRepository
    private lateinit var progressBar: ProgressBar
    private var selectedInterest: String = resources.getString(R.string.white_space)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationScreenBinding.inflate(inflater, container, false)
        initViews()
        changeAppLogo()
        createSpinner()
        fillProgressOnFirstNameChanged()
        fillProgressOnLastNameChanged()
        fillProgressOnEmailChanged()
        emailValidation()
        onGenderButtonSelect()
        getUserRepositoryInstance()
        onButtonRegisterClicked()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun initViews() {
        progressBar = binding.createAccountProgressBar
    }

    private fun getUserRepositoryInstance() {
        userRepository = UserRepository.getInstance(this.context)
    }

    private fun onButtonRegisterClicked() {
        binding.buttonRegister.setOnClickListener {
            binding.createAccountProgressBar.isIndeterminate = true
            when (nameValidation(binding.firstNameEditText, binding.lastNameEditText)) {
                true -> {
                    val user = User(
                        binding.firstNameEditText.text.toString(),
                        binding.lastNameEditText.text.toString(),
                        binding.emailEditText.text.toString(),
                        onGenderButtonSelect(),
                        selectedInterest
                    )
                    userRepository.saveUserData(user)
                    navigateToMainScreen()
                }
                else ->
                    binding.createAccountProgressBar.isIndeterminate = false
            }
        }
    }

    private fun navigateToMainScreen() =
        findNavController().navigate(actionRegistrationScreenFragmentToMainScreen)

    private fun nameValidation(
        firstNameEditText: TextInputEditText,
        lastNameEditText: TextInputEditText
    ): Boolean {
        val firstNameText = firstNameEditText.text.toString()
        val lastNameText = lastNameEditText.text.toString()
        return when {
            firstNameText.isEmpty() -> {
                firstNameEditText.error = resources.getString(R.string.field_required)
                false
            }
            lastNameText.isEmpty() -> {
                firstNameEditText.error = resources.getString(R.string.field_required)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun emailValidation() {
        val emailInput = binding.emailEditText

        emailInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!binding.emailEditText.text.toString().contains(EMAIL_REQUIRED_SIGN)) {
                    binding.emailEditText.error = resources.getString(R.string.enter_valid_email)
                    disableRegisterButton()
                } else {
                    activateRegisterButton()
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun disableRegisterButton() {
        binding.buttonRegister.setHintTextColor(resources.getColor(R.color.disabled_color))
        binding.buttonRegister.isEnabled = false
    }

    private fun activateRegisterButton() {
        binding.buttonRegister.setHintTextColor(resources.getColor(R.color.black))
        binding.buttonRegister.isEnabled = true
    }

    private fun createSpinner() {
        val interestsArray = resources.getStringArray(R.array.interests_array)

        binding.interestsDropDownMenu.adapter =
            ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                interestsArray
            )
        binding.interestsDropDownMenu.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedInterest = interestsArray[position]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.select_option),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
    }

    private fun onGenderButtonSelect(): Gender {
        return when {
            binding.genderManButton.isChecked -> {
                Gender.MAN
            }
            binding.genderWomanButton.isChecked -> {
                Gender.WOMAN
            }
            else -> {
                Gender.OTHER
            }
        }
    }

    private fun fillProgressOnFirstNameChanged() {
        binding.firstNameEditText.addTextChangedListener(object : TextWatcher {
            var isProgressChanged = false
            override fun afterTextChanged(p0: Editable?) {
                if (binding.firstNameEditText.text.toString().isEmpty()) {
                    progressBar.progress -= THIRD_PROGRESS
                    isProgressChanged = false
                } else if (binding.firstNameEditText.text.toString().isNotEmpty()) {
                    if (!isProgressChanged) {
                        progressBar.progress += THIRD_PROGRESS
                        isProgressChanged = true
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
    }

    private fun fillProgressOnLastNameChanged() {
        binding.lastNameEditText.addTextChangedListener(object : TextWatcher {
            var isProgressChanged = false
            override fun afterTextChanged(p0: Editable?) {
                if (binding.lastNameEditText.text.toString().isEmpty()) {
                    progressBar.progress -= THIRD_PROGRESS
                    isProgressChanged = false
                } else if (binding.lastNameEditText.text.toString().isNotEmpty()) {
                    if (!isProgressChanged) {
                        progressBar.progress += THIRD_PROGRESS
                        isProgressChanged = true
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun fillProgressOnEmailChanged() {
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            var isProgressChanged = false
            override fun afterTextChanged(p0: Editable?) {
                if (binding.emailEditText.text.toString().isEmpty()) {
                    progressBar.progress -= THIRD_PROGRESS
                    isProgressChanged = false
                } else if (binding.emailEditText.text.toString().isNotEmpty()) {
                    if (!isProgressChanged) {
                        progressBar.progress += THIRD_PROGRESS
                        isProgressChanged = true
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
    }

    private fun changeAppLogo() {
        when (isEnglishLanguage()) {
            true -> binding.appLogo.setImageResource(R.drawable.app_logo)
            else -> binding.appLogo.setImageResource(R.drawable.bulgarian_app_logo)
        }
    }

    private fun isEnglishLanguage(): Boolean {
        return when (Locale.getDefault().language.lowercase()) {
            resources.getString(R.string.language_english) -> true
            else -> false
        }
    }
}