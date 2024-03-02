package ru.quizplease.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import ru.quizplease.R
import ru.quizplease.databinding.QuizItemViewBinding

class QuizItemView (
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val binding: QuizItemViewBinding

    companion object {
        @JvmStatic
//        private val quizResult = hashMapOf<String, String>()
        private val quizResult = hashSetOf<Int>()
    }

    init {
        val inflate = inflate(context, R.layout.quiz_item_view, this)
        binding = QuizItemViewBinding.bind(inflate)
    }

    fun setQuestion(text: String) {
        binding.questionTextView.text = text
    }

    fun setAnswers(answers:List<String>) {
        binding.answersRadioGroup.also {group ->
            answers.forEach {answer ->
                RadioButton(context).apply {
                    id = View.generateViewId()
                    text = answer
                }.also { group.addView(it) }
            }
        }
    }

    fun setAnswerListener() {
        binding.answersRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
//            quizResult[binding.questionTextView.text.toString()] = radioGroup.findViewById<RadioButton>(i).text.toString()
            quizResult.add(i)
        }
    }

    fun getQuizResult() = quizResult

    fun clearQuizResult() = quizResult.clear()
}