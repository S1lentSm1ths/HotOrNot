package com.vsc.hotornot.view

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.vsc.hotornot.R
import com.vsc.hotornot.databinding.FragmentMotivationScreenBinding

private const val START_OF_TEXT_COLORING = 6
private const val END_OF_TEXT_COLORING = 12
private const val END_OF_TEXT_SIZE = 13
private const val HOT_TEXT_SIZE = 1.2f

class MotivationScreenFragment : Fragment() {

    private lateinit var binding: FragmentMotivationScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMotivationScreenBinding.inflate(inflater, container, false)
        onBackPressed()
        setQuestionWhoIsHot()
        onWhoIsHotButtonClicked()
        return binding.root
    }

    private fun setQuestionWhoIsHot() {
        val whoIsHotText = SpannableString(resources.getString(R.string.who_is_hot))
        whoIsHotText.setSpan(
            RelativeSizeSpan(
                HOT_TEXT_SIZE
            ), START_OF_TEXT_COLORING, END_OF_TEXT_SIZE, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        whoIsHotText.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.who_is_hot_text_color
                )
            ), START_OF_TEXT_COLORING, END_OF_TEXT_COLORING, Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        binding.whoIsHotTextView.text = whoIsHotText
    }

    private fun onWhoIsHotButtonClicked() {
        binding.whoIsHotButton.setOnClickListener {
            backToPreviousScreen()
        }
    }

    private fun backToPreviousScreen() = activity?.findNavController(R.id.navHostFragment)?.popBackStack()

    private fun onBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(this) {
            Toast.makeText(context, resources.getString(R.string.cant_run), Toast.LENGTH_SHORT).show()
        }
    }
}