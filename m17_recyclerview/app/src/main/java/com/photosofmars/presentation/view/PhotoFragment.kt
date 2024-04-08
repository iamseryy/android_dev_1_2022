package com.photosofmars.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.photosofmars.databinding.FragmentPhotoBinding


private const val ARG_ROVER = "rover"
private const val ARG_PHOTO = "photo"
private const val ARG_SOL = "sol"
private const val ARG_DATE = "date"
private const val ARG_CAMERA = "camera"


class PhotoFragment : Fragment() {
    private var rover: String? = null
    private var photo: String? = null
    private var sol: String? = null
    private var date: String? = null
    private var camera: String? = null

    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            rover = it.getString(ARG_ROVER)
            camera= it.getString(ARG_CAMERA)
            photo= it.getString(ARG_PHOTO)
            sol= it.getString(ARG_SOL)
            date= it.getString(ARG_DATE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            roverTextView.text = rover
            solTextView.text = sol
            cameraTextView.text = camera
            dateTextView.text = date

            Glide
                .with(photoImageview.context)
                .load(photo)
                .centerCrop()
                .into(photoImageview)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ROVER, rover)
                    putString(ARG_PHOTO, photo)
                    putString(ARG_SOL, sol)
                    putString(ARG_DATE, date)
                    putString(ARG_CAMERA, camera)
                }
            }
    }
}