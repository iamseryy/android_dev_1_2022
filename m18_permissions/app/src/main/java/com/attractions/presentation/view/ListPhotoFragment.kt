package com.attractions.presentation.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.attractions.Manifest
import com.attractions.R
import com.attractions.databinding.FragmentListPhotoBinding
import com.google.android.material.snackbar.Snackbar


class ListPhotoFragment : Fragment() {
    private var _binding: FragmentListPhotoBinding? = null
    private val binding get() = _binding!!



//    private val launcher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//            view?.let { Snackbar.make(it, "permission is $isGranted", Snackbar.LENGTH_SHORT).show() }
//        }
//
//    private fun checkPermissions() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == )
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}