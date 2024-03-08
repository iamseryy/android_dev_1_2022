package ru.quizplease.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.quizplease.R
import ru.quizplease.databinding.FragmentResultBinding


private const val ARG_QUIZ_RESULT = "quizResult"

class ResultFragment : Fragment() {
    private var quizResult: String? = null

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            quizResult = it.getString(ARG_QUIZ_RESULT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            resultTextview.text = quizResult

            buttonStartAgain.setOnClickListener {
                findNavController().navigate(R.id.action_resultFragment_to_quizFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_QUIZ_RESULT, quizResult)
                }
            }
    }
}