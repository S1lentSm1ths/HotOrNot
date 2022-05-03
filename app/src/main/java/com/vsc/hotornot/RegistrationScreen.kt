package com.vsc.hotornot

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.BoringLayout
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.test.core.app.ApplicationProvider
import com.google.android.material.textfield.TextInputEditText
import com.vsc.hotornot.Constants.TRANSACTION_DURATION_TIME
import com.vsc.hotornot.R.id.actionRegistrationScreenFragmentToMainScreen
import com.vsc.hotornot.R.id.add
import com.vsc.hotornot.databinding.FragmentRegistrationScreenBinding
import kotlinx.coroutines.selects.select


class RegistrationScreen : Fragment() {

    private lateinit var binding: FragmentRegistrationScreenBinding

    private val userSharedPreferences = UserSharedPreferences(this.context)
    private var gender: String? = null
    private var selectedInterest: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationScreenBinding.inflate(inflater, container, false)
        initSpinner()
        checkIfFieldsFill()
        onGenderButtonSelect()
        onButtonRegisterClicked()
        //emailValidation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun firstAndLastNameValidation(
        firstNameEditText: TextInputEditText,
        lastNameEditText: TextInputEditText
    ): Boolean {
        val firstNameText = firstNameEditText.text.toString()
        val lastNameText = lastNameEditText.text.toString()
        return when (firstNameText.isEmpty() && lastNameText.isEmpty()) {
            true -> false
            else -> true
        }
    }

    private fun onButtonRegisterClicked() {
        binding.buttonRegister.setOnClickListener {
            firstAndLastNameValidation(binding.firstNameEditText, binding.lastNameEditText)
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

    private fun initToolBar() {

    }

    private fun emailValidation(): Boolean {
        return when (binding.emailEditText.text.toString().contains("@")) {
            true -> true
            else -> false
        }
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

    private fun fillTheProgressBar() {

    }

    private fun checkIfFieldsFill() {
        var currentProgress = binding.createAccountProgressBar.progress
        val thirdProgress = 33
        binding.firstNameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.createAccountProgressBar.apply {
                    isIndeterminate = false
                    progress = currentProgress + thirdProgress
                }
                currentProgress += thirdProgress
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
        binding.lastNameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.createAccountProgressBar.apply {
                    isIndeterminate = false
                    progress = currentProgress + thirdProgress
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }
}