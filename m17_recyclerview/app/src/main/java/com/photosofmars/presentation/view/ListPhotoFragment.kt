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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListPhotoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


@AndroidEntryPoint
class ListPhotoFragment : Fragment() {

    @Inject
    lateinit var marsPhotoViewModelFactory: MarsPhotoViewModelFactory

    private val viewModel: MarsListPhotoViewModel by viewModels { marsPhotoViewModelFactory }

    private val listPhotoAdapter = ListPhotoAdapter()

    private var _binding: FragmentListPhotoBinding? = null
    private val binding get() = _binding!!



    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListPhotoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListPhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}