package com.vsc.hotornot

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.vsc.hotornot.Constants.TRANSACTION_DURATION_TIME
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
    private lateinit var buttonRegister: Button
    private lateinit var progressBar: ProgressBar
    private var gender = Gender.OTHER
    private var selectedInterest: String = resources.getString(R.string.white_space)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationScreenBinding.inflate(inflater, container, false)
        initViews()
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
        buttonRegister = binding.buttonRegister
        progressBar = binding.createAccountProgressBar
    }

    private fun getUserRepositoryInstance() {
        userRepository = UserRepository.getInstance(this.context)
    }

    private fun onButtonRegisterClicked() {
        buttonRegister.setOnClickListener {
            binding.createAccountProgressBar.isIndeterminate = true
            when (nameValidation(binding.firstNameEditText, binding.lastNameEditText)) {
                true -> {
                    val user = User(
                        binding.firstNameEditText.text.toString(),
                        binding.lastNameEditText.text.toString(),
                        binding.emailEditText.text.toString(),
                        gender,
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
        buttonRegister.setHintTextColor(resources.getColor(R.color.disabled_color))
        buttonRegister.isEnabled = false
    }

    private fun activateRegisterButton() {
        buttonRegister.setHintTextColor(resources.getColor(R.color.black))
        buttonRegister.isEnabled = true
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

    private fun onGenderButtonSelect() {
        binding.genderManButton.setOnClickListener {
            gender = Gender.MAN
        }
        binding.genderWomanButton.setOnClickListener {
            gender = Gender.WOMAN
        }
        binding.genderOtherButton.setOnClickListener {
            gender = Gender.OTHER
        }
    }

    private fun fillProgressOnFirstNameChanged() {
        binding.firstNameEditText.addTextChangedListener(object : TextWatcher {
            var progressChanged = false
            override fun afterTextChanged(p0: Editable?) {
                if (binding.firstNameEditText.text.toString().isEmpty()) {
                    progressBar.progress -= THIRD_PROGRESS
                    progressChanged = false
                } else if (binding.firstNameEditText.text.toString().isNotEmpty()) {
                    if (!progressChanged) {
                        progressBar.progress += THIRD_PROGRESS
                        progressChanged = true
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
    }

    private fun fillProgressOnLastNameChanged() {
        binding.lastNameEditText.addTextChangedListener(object : TextWatcher {
            var progressChanged = false
            override fun afterTextChanged(p0: Editable?) {
                if (binding.lastNameEditText.text.toString().isEmpty()) {
                    progressBar.progress -= THIRD_PROGRESS
                    progressChanged = false
                } else if (binding.lastNameEditText.text.toString().isNotEmpty()) {
                    if (!progressChanged) {
                        progressBar.progress += THIRD_PROGRESS
                        progressChanged = true
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun fillProgressOnEmailChanged() {
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            var progressChanged = false
            override fun afterTextChanged(p0: Editable?) {
                if (binding.emailEditText.text.toString().isEmpty()) {
                    progressBar.progress -= THIRD_PROGRESS
                    progressChanged = false
                } else if (binding.emailEditText.text.toString().isNotEmpty()) {
                    if (!progressChanged) {
                        progressBar.progress += THIRD_PROGRESS
                        progressChanged = true
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
    }
}