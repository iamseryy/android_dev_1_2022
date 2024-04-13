package com.attractions.presentation.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.attractions.R
import com.attractions.databinding.FragmentListPhotoBinding
import com.attractions.entity.Photo
import com.attractions.presentation.adapter.ListPhotoAdapter
import com.attractions.presentation.viewmodel.ListPhotoViewModel
import com.attractions.presentation.viewmodel.ListPhotoViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject


private const val FORMAT = "yyyy-MM-dd"


@AndroidEntryPoint
class ListPhotoFragment : Fragment() {

    @Inject
    lateinit var listPhotoViewModelFactory: ListPhotoViewModelFactory
    private val viewModel: ListPhotoViewModel by viewModels { listPhotoViewModelFactory }

    private var _binding: FragmentListPhotoBinding? = null
    private val binding get() = _binding!!

    private val listPhotoAdapter = ListPhotoAdapter(onItemClicked = { photo -> onItemClicked(photo)})


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
            listPhotoAdapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        binding.takePhotoButton.setOnClickListener {
            findNavController().navigate(R.id.action_listPhotoFragment_to_photographFragment)
        }

    }

    private fun onItemClicked(photo: Photo) {
        val bundle = Bundle().apply {
            putString("uri", photo.uri)
            putString("date", SimpleDateFormat(FORMAT, Locale.getDefault()).format(photo.date.time))
        }
        findNavController().navigate(R.id.action_listPhotoFragment_to_photoFragment, bundle)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}