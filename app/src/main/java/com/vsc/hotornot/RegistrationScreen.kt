package com.vsc.hotornot

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.vsc.hotornot.Constants.TRANSACTION_DURATION_TIME
import com.vsc.hotornot.R.id.actionRegistrationScreenFragmentToMainScreen
import com.vsc.hotornot.databinding.FragmentRegistrationScreenBinding
import com.vsc.hotornot.model.User


class RegistrationScreen : Fragment() {

    private lateinit var binding: FragmentRegistrationScreenBinding
    private lateinit var userSharedPreferences: UserSharedPreferences
    private lateinit var buttonRegister: Button
    private var gender: String = "Other"
    private var selectedInterest: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationScreenBinding.inflate(inflater, container, false)
        initViews()
        initSpinner()
        onFieldsFill()
        emailValidation()
        onGenderButtonSelect()
        getUserSharedPreferencesInstance()
        onButtonRegisterClicked()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun initViews() {
        buttonRegister = binding.buttonRegister
    }

    private fun getUserSharedPreferencesInstance() {
        userSharedPreferences = UserSharedPreferences(activity)
    }

    private fun onButtonRegisterClicked() {
        buttonRegister.setOnClickListener {
            binding.createAccountProgressBar.isIndeterminate = true
            when (firstAndLastNameValidation(binding.firstNameEditText, binding.lastNameEditText)) {
                true -> {
                    val user = User(
                        binding.firstNameEditText.text.toString(),
                        binding.lastNameEditText.text.toString(),
                        binding.emailEditText.text.toString(),
                        gender,
                        selectedInterest
                    )
                    userSharedPreferences.saveUserData(user)
                    postTransactionDelayToMainScreen()
                }
                else ->
                    binding.createAccountProgressBar.isIndeterminate = false
            }
        }
    }

    private fun postTransactionDelayToMainScreen() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                navigateToMainScreen()
            }, TRANSACTION_DURATION_TIME)
    }

    private fun navigateToMainScreen() {
        findNavController().navigate(actionRegistrationScreenFragmentToMainScreen)
    }

    private fun firstAndLastNameValidation(
        firstNameEditText: TextInputEditText,
        lastNameEditText: TextInputEditText
    ): Boolean {
        val firstNameText = firstNameEditText.text.toString()
        val lastNameText = lastNameEditText.text.toString()
        return when {
            firstNameText.isEmpty() -> {
                firstNameEditText.error = "Field is required"
                false
            }
            lastNameText.isEmpty() -> {
                firstNameEditText.error = "Field is required"
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
                if (!binding.emailEditText.text.toString().contains("@")) {
                    binding.emailEditText.error = "Please enter a valid email!"
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

    private fun initSpinner() {
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
                    Toast.makeText(requireContext(), "Please select option!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun onGenderButtonSelect() {
        binding.genderManButton.setOnClickListener() {
            gender = binding.genderManButton.text.toString()
        }
        binding.genderWomanButton.setOnClickListener() {
            gender = binding.genderWomanButton.text.toString()
        }
        binding.genderOtherButton.setOnClickListener() {
            gender = binding.genderOtherButton.text.toString()
        }
    }

    private fun onFieldsFill() {
        val progressBar = binding.createAccountProgressBar
        val thirdProgress = 33
        binding.firstNameEditText.addTextChangedListener(object : TextWatcher {
            var progressChanged = false
            override fun afterTextChanged(p0: Editable?) {
                if (binding.firstNameEditText.text.toString().isEmpty()) {
                    progressBar.progress -= thirdProgress
                    progressChanged = false
                } else if (binding.firstNameEditText.text.toString().isNotEmpty()) {
                    if (!progressChanged) {
                        progressBar.progress += thirdProgress
                        progressChanged = true
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
        binding.lastNameEditText.addTextChangedListener(object : TextWatcher {
            var progressChanged = false
            override fun afterTextChanged(p0: Editable?) {
                if (binding.lastNameEditText.text.toString().isEmpty()) {
                    progressBar.progress -= thirdProgress
                    progressChanged = false
                } else if (binding.lastNameEditText.text.toString().isNotEmpty()) {
                    if (!progressChanged) {
                        progressBar.progress += thirdProgress
                        progressChanged = true
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            var progressChanged = false
            override fun afterTextChanged(p0: Editable?) {
                if (binding.emailEditText.text.toString().isEmpty()) {
                    progressBar.progress -= thirdProgress
                    progressChanged = false
                } else if (binding.emailEditText.text.toString().isNotEmpty()) {
                    if (!progressChanged) {
                        progressBar.progress += thirdProgress
                        progressChanged = true
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
    }
}