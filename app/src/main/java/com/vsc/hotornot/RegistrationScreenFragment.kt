package com.vsc.hotornot

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vsc.hotornot.databinding.FragmentRegistrationScreenBinding

class RegistrationScreenFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationScreenBinding.inflate(inflater, container, false)
        onButtonRegisterClicked()
        return binding.root
    }

    private fun onButtonRegisterClicked(){
        binding.buttonRegister.setOnClickListener {
            saveUserData()
        }
    }

    private fun saveUserData(){
        val userSharedPreferences = activity?.getSharedPreferences(getString(R.string.user_shared_preferences_key), Context.MODE_PRIVATE)
        val editor = userSharedPreferences?.edit()
        editor?.apply {
            putString("name", binding.firstNameEditText.toString())
            putString("last_name", binding.lastNameEditText.toString())
        }?.apply()
    }
}