package com.vsc.hotornot

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vsc.hotornot.databinding.FragmentMainScreenBinding

class MainScreen : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        changePersonImageOnClick()
        onButtonDeleteUserClicked()
    }

    private fun changePersonImageOnClick() {
        val personImage = binding.personImage
        binding.personHotButton.setOnClickListener {
            personImage.setImageResource(R.drawable.second_person_img)
            binding.personName.text = getString(R.string.name_stan)
            checkPersonName()
        }
        binding.personNotButton.setOnClickListener {
            personImage.setImageResource(R.drawable.third_person_img)
            binding.personName.text = getString(R.string.name_georgi)
            checkPersonName()
        }
    }

    private fun checkPersonName() {
        val personNameText = binding.personName.text
        if (personNameText == "Georgi") {
            binding.personNotButton.visibility = View.GONE
            binding.personHotButton.visibility = View.VISIBLE
        } else if (personNameText == "Stan") {
            binding.personHotButton.visibility = View.GONE
            binding.personNotButton.visibility = View.VISIBLE
        }
    }

    private fun onButtonDeleteUserClicked() {
        binding.buttonDeleteUser.setOnClickListener {
            deleteUser()
        }
    }

    private fun deleteUser() {
        val userSharedPreferences = activity?.getSharedPreferences(
            Constants.userSharedPreferencesKey,
            Context.MODE_PRIVATE
        )
        val editor = userSharedPreferences?.edit()
        editor?.apply() {
            remove("first_name")
            remove("last_name")
        }?.apply()
    }
}