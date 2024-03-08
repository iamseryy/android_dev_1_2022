package ru.quizplease.ui

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import ru.quizplease.R
import ru.quizplease.databinding.FragmentStartBinding
import java.text.SimpleDateFormat

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    private val calendar = Calendar.getInstance()
    @SuppressLint("SimpleDateFormat")
    private val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")

    companion object {
        const val BIRTHDATE = "Date of Birth"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonContinue.setOnClickListener {
                    findNavController().navigate(R.id.action_startFragment_to_quizFragment)
            }

            buttonBirthdate.setOnClickListener {
                showBirthDateDialog(it)
            }
        }
    }

    private fun showBirthDateDialog(view: View) {
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(resources.getString(R.string.select_your_date_of_birth))
            .build().apply {
                addOnPositiveButtonClickListener {
                    calendar.timeInMillis = it
                    Snackbar.make(view, simpleDateFormat.format(calendar.time), Snackbar.LENGTH_LONG).show()
                }
            }
            .show(parentFragmentManager, BIRTHDATE)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}