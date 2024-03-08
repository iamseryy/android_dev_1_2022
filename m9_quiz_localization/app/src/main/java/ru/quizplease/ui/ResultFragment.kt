package ru.quizplease.ui

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
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
            animateText(resultTextview)

            buttonStartAgain.setOnClickListener {
                findNavController().navigate(R.id.action_resultFragment_to_quizFragment)
            }.also {
                animateButton(buttonStartAgain)
            }
        }
    }


    private fun animateText(view: TextView) {
        ObjectAnimator.ofArgb(
            view,
            "textColor",
            ContextCompat.getColor(view.context,R.color.orange_500),
            ContextCompat.getColor(view.context,R.color.green_700)
        ). apply {
                duration = 2000
                interpolator = AccelerateInterpolator()
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
                start()
        }
    }

    private fun animateButton(materialButton: MaterialButton){
        (AnimatorInflater.loadAnimator(this.context, R.animator.result_button_start_again_animator) as AnimatorSet)
            .apply {
                setTarget(materialButton)
                start()
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