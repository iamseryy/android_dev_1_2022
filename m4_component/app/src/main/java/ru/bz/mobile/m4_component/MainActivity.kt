package ru.bz.mobile.m4_component

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
            buttonControl()
        }

        binding.phoneTextInputEditText.doOnTextChanged { _, _, _, _ ->
            buttonControl()
        }

        binding.maleRadioButton.setOnCheckedChangeListener { _, _ ->
            buttonControl()
        }

        binding.femaleRadioButton.setOnCheckedChangeListener { _, _ ->
            buttonControl()
        }

        binding.notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.authorizationCheckBox.isEnabled = true
                binding.aboutNewProductsCheckBox.isEnabled = true
            } else {
                binding.authorizationCheckBox.isEnabled = false
                binding.aboutNewProductsCheckBox.isEnabled = false
            }

            buttonControl()
        }

        binding.authorizationCheckBox.setOnCheckedChangeListener { _, _, ->
            buttonControl()
        }

        binding.aboutNewProductsCheckBox.setOnCheckedChangeListener { _, _ ->
            buttonControl()
        }

        binding.saveButton.setOnClickListener {
            Snackbar.make(it, resources.getString(R.string.saved), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun init() {
        with(Random.nextInt(101)) {
            binding.pointsProgressIndicator.progress = this
            binding.progressIndicatorValueTextView.text = resources.getString(R.string._100, this)
        }

        buttonControl()
    }

    private fun isSaveButtonAvailable() = isNameValid() && isPhoneValid() &&
            isGenderSelected() && isNotificationValid()

    private fun isNameValid() = binding.nameTextInputEditText.text.let { !it.isNullOrEmpty() && it.length <= 40 }

    private fun isPhoneValid() = !binding.phoneTextInputEditText.text.isNullOrEmpty()

    private fun isGenderSelected() = binding.maleRadioButton.isChecked || binding.femaleRadioButton.isChecked

    private fun isNotificationValid() = binding.notificationSwitch.isChecked &&
               (binding.aboutNewProductsCheckBox.isChecked || binding.authorizationCheckBox.isChecked)

    private fun buttonControl() {
        binding.saveButton.isEnabled = isSaveButtonAvailable()
    }
}