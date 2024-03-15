package ru.m11_timer_data_storage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.m11_timer_data_storage.databinding.FragmentRepoDemoBinding
import ru.m11_timer_data_storage.repository.Repository


class RepoDemoFragment : Fragment() {
    private lateinit var repository: Repository
    private var _binding: FragmentRepoDemoBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepoDemoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = Repository(requireContext())

        binding.apply {
            repoValueTextView.text = repository.getText()

            saveButton.setOnClickListener{
                repository.saveText(textInputEditText.text.toString())
                repoValueTextView.text = textInputEditText.text.toString()
                textInputEditText.text?.clear()
            }

            clearButton.setOnClickListener {
                repository.clearText()
                repoValueTextView.text = ""
                textInputEditText.text?.clear()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}