package com.example.m2_layout.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.m2_layout.databinding.CustomViewBinding

class CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private val binding = CustomViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setTopLineTextView(textLine: String) {
        binding.topLineTextView.text = textLine
    }

    fun setBottomLineTextView(textLine: String) {
        binding.bottomLineTextView.text = textLine
    }
}