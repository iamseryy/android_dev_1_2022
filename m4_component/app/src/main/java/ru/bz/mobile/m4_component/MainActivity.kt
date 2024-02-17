package ru.bz.mobile.m4_component

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import ru.bz.mobile.m4_component.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

        binding.nameTextInputEditText.doOnTextChanged { _, _, _, _ ->
//            binding.saveButton.isEnabled = isSaveButtonAvailable()
        }

        binding.notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.authorizationCheckBox.isEnabled = true
                binding.aboutNewProductsCheckBox.isEnabled = true
            } else {
                binding.authorizationCheckBox.isEnabled = false
                binding.aboutNewProductsCheckBox.isEnabled = false
            }
        }

//        binding.saveButton.setOnClickListener {
//            Snackbar.make(it, resources.getString(R.string.saved), Snackbar.LENGTH_SHORT).show()
//        }
    }

    private fun init() {
        with(Random.nextInt(101)) {
            binding.pointsProgressIndicator.progress = this
            binding.progressIndicatorValueTextView.text = resources.getString(R.string._100, this)
        }

        val test = isSaveButtonAvailable()

//        binding.saveButton.isEnabled = isSaveButtonAvailable()
    }

    private fun isSaveButtonAvailable(): Boolean {
        return isNameValid()
    }

    private fun isNameValid() = binding.nameTextInputEditText.text.let { !it.isNullOrEmpty() }

//    private fun ButtonControl() {
//        binding.saveButton.isEnabled = isSaveButtonAvailable()
//    }



}