package com.attractions.presentation.view



import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat

import com.attractions.databinding.FragmentListPhotoBinding
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.Executor


class ListPhotoFragment : Fragment() {
    private var _binding: FragmentListPhotoBinding? = null
    private val binding get() = _binding!!

    private lateinit var executor: Executor
    private lateinit var imageCapture: ImageCapture




    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            startCamera()
            view?.let { Snackbar.make(it, "permission is $isGranted", Snackbar.LENGTH_SHORT).show() }
        }

    private fun checkPermissions() {
        if (context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.CAMERA) } == PackageManager.PERMISSION_GRANTED) {
            startCamera()
            view?.let { Snackbar.make(it, "permission is Granted", Snackbar.LENGTH_SHORT).show() }
        } else {
            launcher.launch(Manifest.permission.CAMERA)
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

        executor = context?.let { ContextCompat.getMainExecutor(it) }!!

        checkPermissions()

        binding.button.setOnClickListener {
            takePhoto()
        }



    }

    private fun takePhoto() {
        TODO("Not yet implemented")
    }

    private fun startCamera() {
        context?.let { ProcessCameraProvider.getInstance(it) }?.addListener({
            val cameraProvider = it.
        }, executor)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}