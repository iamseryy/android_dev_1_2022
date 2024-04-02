package ru.bored.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.bored.databinding.FragmentMainBinding
import ru.bored.presentation.viewmodel.MainViewModel
import ru.bored.presentation.viewmodel.MainViewModelFactory
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels { mainViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(savedInstanceState == null) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.reloadUsefulActivity()
            }
        }

        binding.apply {
            refreshButton.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.reloadUsefulActivity()
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.usefulActivityFlow.collect() {
                    activityTextview.text = it?.activity
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}