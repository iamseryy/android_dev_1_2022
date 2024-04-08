package com.photosofmars.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.photosofmars.databinding.FragmentListPhotoBinding
import com.photosofmars.presentation.adapter.ListPhotoAdapter
import com.photosofmars.presentation.viewmodel.MarsListPhotoViewModel
import com.photosofmars.presentation.viewmodel.MarsPhotoViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ListPhotoFragment : Fragment() {

    @Inject
    lateinit var marsPhotoViewModelFactory: MarsPhotoViewModelFactory

    private val viewModel: MarsListPhotoViewModel by viewModels { marsPhotoViewModelFactory }

    private val listPhotoAdapter = ListPhotoAdapter()

    private var _binding: FragmentListPhotoBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.photosRecyclerview.adapter = listPhotoAdapter

        viewModel.photos.onEach {
            listPhotoAdapter.setPhotos(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collect{
                binding.progressBar.isVisible = it
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collect {
                Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}