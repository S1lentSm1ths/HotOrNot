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
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.text.toSpannable
import com.vsc.hotornot.databinding.FragmentMotivationScreenBinding

class MotivationScreenFragment : Fragment() {

    private lateinit var binding: FragmentMotivationScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMotivationScreenBinding.inflate(inflater, container, false)
        setQuestionWhoIsHot()
        return binding.root
    }

    private fun setQuestionWhoIsHot() {
        val whoIsHotText = SpannableString(resources.getString(R.string.who_is_hot))
        val startOfTextColoring = 7
        val endOfTextColoring = 11
        whoIsHotText.setSpan(
            RelativeSizeSpan(
                resources.getInteger(R.integer.whoIsHotTextSize).toFloat()
            ), startOfTextColoring, endOfTextColoring, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        whoIsHotText.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.who_is_hot_text_color
                )
            ), startOfTextColoring, endOfTextColoring, Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
    }
}