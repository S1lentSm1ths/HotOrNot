package com.vsc.hotornot

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.vsc.hotornot.Constants.Companion.transactionDurationTime
import com.vsc.hotornot.databinding.FragmentRegistrationScreenBinding

class RegistrationScreen : Fragment() {

    private lateinit var binding: FragmentRegistrationScreenBinding
    private val userSharedPreferences = UserSharedPreferences()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationScreenBinding.inflate(inflater, container, false)
        onButtonRegisterClicked()
        return binding.root
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
                userSharedPreferences.saveUserData(firstNameEditText, lastNameEditText)
                Toast.makeText(
                    this.context,
                    "You have been registered successful!",
                    Toast.LENGTH_SHORT
                ).show()
                postTransactionDelayToMainScreen()
            }
        }
    }

    private fun onButtonRegisterClicked() {
        binding.buttonRegister.setOnClickListener {
            checkIfEnteredDataIsNull(binding.firstNameEditText, binding.lastNameEditText)
        }
    }

    private fun postTransactionDelayToMainScreen() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                findNavController().navigate(R.id.actionRegistrationScreenFragmentToMainScreen)
            }, transactionDurationTime)
    }
}