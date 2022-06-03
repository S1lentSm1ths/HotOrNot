package com.vsc.hotornot

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.text.toSpannable
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.vsc.hotornot.databinding.FragmentMotivationScreenBinding
import kotlin.math.max

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
        val startOfTextColoring = 7
        val endOfTextColoring = 12
        val endOfTextSize = 14
        val hotTextSize = 1.2f
        val questionMarkSize = 1.0f
        whoIsHotText.setSpan(
            RelativeSizeSpan(
                hotTextSize
            ), startOfTextColoring, endOfTextSize, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        whoIsHotText.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.who_is_hot_text_color
                )
            ), startOfTextColoring, endOfTextColoring, Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        binding.whoIsHotTextView.text = whoIsHotText
    }

    private fun onWhoIsHotButtonClicked() {
        binding.whoIsHotButton.setOnClickListener {
            backToPreviousScreen()
        }
    }

    private fun backToPreviousScreen() {
        activity?.supportFragmentManager?.popBackStack()
    }

    private fun onBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(this) {
            Toast.makeText(context, resources.getString(R.string.cant_run), Toast.LENGTH_SHORT).show()
        }
    }
}