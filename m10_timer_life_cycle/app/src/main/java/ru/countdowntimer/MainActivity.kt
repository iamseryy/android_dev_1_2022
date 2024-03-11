package ru.countdowntimer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.Slider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.countdowntimer.databinding.ActivityMainBinding
import kotlin.math.roundToInt


private const val PROGRESS_BAR_COUNTER_DEFAULT = 100
private const val COUNT_FROM_DEFAULT = 10
private const val COUNT_TO_DEFAULT = 0
private const val COUNT_TO_KEY = "countTo"
private const val IS_COUNTER_STARTED_KEY = "isCounterStarted"
private const val COUNTER_PROGRESS_BAR_KEY = "counterProgressBar"
private const val COUNTER_PROGRESS_LABEL_KEY = "counterProgressLabel"


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var job: Job? = null

    private var countFrom = COUNT_FROM_DEFAULT
    private var countTo = COUNT_TO_DEFAULT
    private var progressBarCounter = PROGRESS_BAR_COUNTER_DEFAULT
    private var progressBarCounterLabel = COUNT_FROM_DEFAULT.toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            counterSlider.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
                countFrom = value.roundToInt()
                progressBarCounterLabel = countFrom.toString()
                counterValueTextview.text  = progressBarCounterLabel
                counterProgressBar.progress = PROGRESS_BAR_COUNTER_DEFAULT
            })

            startButton.setOnClickListener {
                if(!isCounterStarted()) {
                    startCounter()
                } else {
                    stopCounter()
                }
            }
        }
    }

    private fun isCounterStarted() = !(job == null || !job?.isActive!!)

    private fun startCounter() {
        if (countTo == 0) {
            progressBarCounterLabel = countFrom.toString()
            progressBarCounter = PROGRESS_BAR_COUNTER_DEFAULT
            binding.apply {
                counterValueTextview.text  = progressBarCounterLabel
                counterProgressBar.progress = progressBarCounter
            }
        }

        job = CoroutineScope(Dispatchers.Main).launch {
            (countTo + 1..countFrom).forEach {
                delay(1000)
                binding.apply {
                    progressBarCounter = PROGRESS_BAR_COUNTER_DEFAULT -
                            (PROGRESS_BAR_COUNTER_DEFAULT * it / countFrom)
                    counterProgressBar.progress = progressBarCounter
                    progressBarCounterLabel = (countFrom - it).toString()
                    counterValueTextview.text = progressBarCounterLabel
                    countTo = it
                }
            }

            binding.apply {
                counterSlider.isEnabled = true
                startButton.text = resources.getString(R.string.start)
            }
            countTo = 0

        }

        binding.apply {
            counterSlider.isEnabled = false
            startButton.text = resources.getString(R.string.stop)
        }
    }

    private fun stopCounter() {
        job?.cancel()
        binding.apply {
            counterSlider.isEnabled = true
            startButton.text = resources.getString(R.string.start)
        }
        countTo = 0
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(IS_COUNTER_STARTED_KEY, isCounterStarted())
        outState.putInt(COUNT_TO_KEY, countTo)
        outState.putInt(COUNTER_PROGRESS_BAR_KEY, progressBarCounter)
        outState.putString(COUNTER_PROGRESS_LABEL_KEY, progressBarCounterLabel)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        countTo = savedInstanceState.getInt(COUNT_TO_KEY)
        progressBarCounter = savedInstanceState.getInt(COUNTER_PROGRESS_BAR_KEY)
        progressBarCounterLabel = savedInstanceState.getString(COUNTER_PROGRESS_LABEL_KEY)!!
        binding.apply {
            counterProgressBar.progress = progressBarCounter
            counterValueTextview.text  = progressBarCounterLabel
        }

        if(savedInstanceState.getBoolean(IS_COUNTER_STARTED_KEY)) {
            startCounter()
        }
    }
}