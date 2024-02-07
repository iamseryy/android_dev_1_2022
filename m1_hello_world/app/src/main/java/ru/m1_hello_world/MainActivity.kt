package ru.m1_hello_world

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import ru.m1_hello_world.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var counter = 0

    companion object {
        const val MAX_COUNTER = 50
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reduceButton.isEnabled = false
        binding.cancelButton.isVisible = false

        binding.addButton.setOnClickListener {
            setContentAddButton(binding)
        }

        binding.reduceButton.setOnClickListener {
            setContentReduceButton(binding)
        }

        binding.cancelButton.setOnClickListener {
            setContentCancelButton(binding)
        }
    }

    private fun setContentAddButton(binding: ActivityMainBinding) {
        counter++

        if (counter > MAX_COUNTER) {
            binding.remainderTextView.text = resources.getString(R.string.overflow)
            binding.remainderTextView.setTextColor(getColor(R.color.red))
            binding.cancelButton.isVisible = true
        }

        setCommonContentForAddReduce(binding)
    }


    private fun setContentReduceButton(binding: ActivityMainBinding) {
        counter--

        if (counter == 0) {
            binding.reduceButton.isEnabled = false
            binding.remainderTextView.text = resources.getString(R.string.free)
            binding.remainderTextView.setTextColor(getColor(R.color.green))
        }

        setCommonContentForAddReduce(binding)
    }

    private fun setContentCancelButton(binding: ActivityMainBinding) {
        counter = 0
        binding.reduceButton.isEnabled = false
        binding.cancelButton.isVisible = false
        binding.counterTextView.text = "0"
        binding.remainderTextView.text = resources.getString(R.string.free)
        binding.remainderTextView.setTextColor(getColor(R.color.green))
    }

    private fun setCommonContentForAddReduce(binding: ActivityMainBinding) {
        if(counter > 0) binding.reduceButton.isEnabled = true
        if(counter in 1..< MAX_COUNTER) {
            binding.cancelButton.isVisible = false
            binding.remainderTextView.text = resources.getString(R.string.spacesLeft) + " ${MAX_COUNTER - counter}"
            binding.remainderTextView.setTextColor(getColor(R.color.blue))
        }

        if (counter == MAX_COUNTER) {
            binding.remainderTextView.text = resources.getString(R.string.full)
            binding.remainderTextView.setTextColor(getColor(R.color.blue))
            binding.cancelButton.isVisible = false
        }

        binding.counterTextView.text = counter.toString()
    }
}