package ru.quizplease.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.quizplease.R
import ru.quizplease.databinding.FragmentQuizBinding
import ru.quizplease.quiz.Question
import ru.quizplease.quiz.QuizStorage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private val quiz = QuizStorage.getQuiz(QuizStorage.Locale.Ru)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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
                setQuestion(question.question)
                setAnswers(question.answers)
                setAnswerListener()
                clearQuizResult()
            }.also {  binding.quizContainer.addView(it, index) }
        }
    }

    private fun setListeners() {
        binding.buttonSend.setOnClickListener {
            val quizResult = QuizItemView(requireContext(), null).getQuizResult()
            if (quizResult.size < quiz.questions.size) {
                Toast.makeText(context, resources.getString(R.string.all_questions_must_be_answered), Toast.LENGTH_SHORT).show()
            } else {
                val bundle = Bundle().apply {
                    val result = QuizStorage.answer(quiz, quizResult.toList())
                    putString("quizResult", result)
                }
                findNavController().navigate(R.id.action_quizFragment_to_resultFragment, bundle)
            }
        }

        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_quizFragment_to_startFragment)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuizFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuizFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}