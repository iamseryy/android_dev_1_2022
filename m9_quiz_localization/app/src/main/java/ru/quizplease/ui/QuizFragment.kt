package ru.quizplease.ui

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import ru.quizplease.R
import ru.quizplease.databinding.FragmentQuizBinding
import ru.quizplease.quiz.Question
import ru.quizplease.quiz.QuizStorage
import java.util.Locale


class QuizFragment : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private val quiz = QuizStorage.getQuiz(Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buildQuizView(quiz.questions)
        setListeners()
    }

    private fun buildQuizView(questions: List<Question>) {
        questions.forEachIndexed { index, question ->
            QuizItemView(requireContext(), null).apply {

                ObjectAnimator.ofFloat(this, View.ALPHA, 0F, 1F).apply {
                    duration = 1500L * (index + 1)
                }.start()

                setQuestion(question.question)
                setAnswers(question.answers)
                setAnswerListener()
                clearQuizResult()
            }.also {  binding.quizContainer.addView(it, index) }
        }
    }

    private fun setListeners() {
        binding.apply {
            buttonSend.setOnClickListener {
                val quizResult = QuizItemView(requireContext(), null).getQuizResult()
                if (quizResult.size < quiz.questions.size) {
                    Toast.makeText(context, resources.getString(R.string.all_questions_must_be_answered), Toast.LENGTH_SHORT).show()
                } else {
                    val bundle = Bundle().apply {
                        QuizStorage.answer(quiz, quizResult.values.toList()).apply {
                            putString("quizResult", this)
                        }
                    }
                    findNavController().navigate(R.id.action_quizFragment_to_resultFragment, bundle)
                }
            }

            buttonBack.setOnClickListener {
                findNavController().navigate(R.id.action_quizFragment_to_startFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}