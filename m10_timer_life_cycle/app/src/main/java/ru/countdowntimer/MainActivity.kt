package ru.countdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.countdowntimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setDefault()




        binding.apply {
            counterSlider.va

            startButton.setOnClickListener {
                if(job?.isActive == true ) {
                    job?.cancel()
                    startButton.text = resources.getString(R.string.start)
                } else {
                    startButton.text = resources.getString(R.string.stop)

                    job = CoroutineScope(Dispatchers.Main).launch {
                        (0..100).forEach{
                            delay(1000)
                            counterProgressBar.progress = 100 - it
                            counterText.text = (100 - it).toString()
                        }
                    }

                }
            }
        }
    }

    private fun setDefault() {
        binding.apply {
            counterProgressBar.progress = 100
            counterText.text = "10"
            counterSlider.value = 10F
        }
    }
}