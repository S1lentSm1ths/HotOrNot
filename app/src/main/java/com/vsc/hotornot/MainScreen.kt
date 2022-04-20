package com.vsc.hotornot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        when (binding.personName.text) {
            "Georgi" -> {
                binding.personNotButton.visibility = View.GONE
                binding.personHotButton.visibility = View.VISIBLE
            }
            "Stan" -> {
                binding.personHotButton.visibility = View.GONE
                binding.personNotButton.visibility = View.VISIBLE
            }
            else -> {
                binding.personNotButton.visibility = View.VISIBLE
                binding.personHotButton.visibility = View.VISIBLE
            }
        }
    }
}