package com.example.m5_quiz_resources

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.m5_quiz_resources.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonContinue.setOnClickListener{
            Snackbar.make(it, resources.getString(R.string.to_be_continued), Snackbar.LENGTH_SHORT).show()
        }
    }
}