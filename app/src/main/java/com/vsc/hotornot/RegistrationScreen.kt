package com.vsc.hotornot

import android.R
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.test.core.app.ApplicationProvider
import com.google.android.material.textfield.TextInputEditText
import com.vsc.hotornot.Constants.Companion.transactionDurationTime
import com.vsc.hotornot.databinding.FragmentRegistrationScreenBinding
import com.vsc.hotornot.R.id.actionRegistrationScreenFragmentToMainScreen


class RegistrationScreen : Fragment() {

    private lateinit var binding: FragmentRegistrationScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationScreenBinding.inflate(inflater, container, false)
        onButtonRegisterClicked()
        //emailValidation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun checkIfEnteredDataIsNull(
        firstNameEditText: TextInputEditText,
        lastNameEditText: TextInputEditText
    ) {
        val firstNameText = firstNameEditText.text.toString()
        val lastNameText = lastNameEditText.text.toString()
        when {
            firstNameText.isEmpty() -> {
                firstNameEditText.error = "Field is required!"
            }
            lastNameText.isEmpty() -> {
                lastNameEditText.error = "Field is required!"
            }
            else -> {
                saveUserData(firstNameEditText, lastNameEditText)
                Toast.makeText(
                    this.context,
                    "You have been registered successful!",
                    Toast.LENGTH_SHORT
                ).show()
                postTransactionDelayToMainScreen()
            }
        }
    }

    private fun saveUserData(firstNameEditText: TextInputEditText, lastNameEditText: TextInputEditText) {
        val userSharedPreferences = activity?.getSharedPreferences(
            Constants.userSharedPreferencesKey,
            Context.MODE_PRIVATE
        )
        val editor = userSharedPreferences?.edit()
        editor?.apply {
            putString("first_name", firstNameEditText.toString())
            putString("last_name", lastNameEditText.toString())
        }?.apply()
    }

    private fun onButtonRegisterClicked() {
        binding.buttonRegister.setOnClickListener {
            checkIfEnteredDataIsNull(binding.firstNameEditText, binding.lastNameEditText)
        }
    }

    private fun postTransactionDelayToMainScreen() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                findNavController().navigate(actionRegistrationScreenFragmentToMainScreen)
            }, transactionDurationTime)
    }

    private fun initToolBar() {

    }

    private fun emailValidation() {
        val emailValidate = binding.emailEditText
        val email = emailValidate.text.toString().trim { it <= ' ' }
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

        emailValidate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (email.matches(emailPattern) && s.length > 0) {
                    Toast.makeText(
                        ApplicationProvider.getApplicationContext<Context>(),
                        "valid email address",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        ApplicationProvider.getApplicationContext<Context>(),
                        "Invalid email address",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}