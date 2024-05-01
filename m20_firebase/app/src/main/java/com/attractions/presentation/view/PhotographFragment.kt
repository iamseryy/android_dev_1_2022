package com.attractions.presentation.view

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.attractions.R
import com.attractions.databinding.FragmentPhotographBinding
import com.attractions.entity.Photo
import com.attractions.presentation.viewmodel.PhotographViewModel
import com.attractions.presentation.viewmodel.PhotographViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import javax.inject.Inject


private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss"
private const val DATE_PHOTO_FORMAT = "yyyy-MM-dd"
private const val TYPE = "image/jpeg"


@AndroidEntryPoint
class PhotographFragment : Fragment() {

    @Inject
    lateinit var photographViewModelFactory: PhotographViewModelFactory
    private val viewModel: PhotographViewModel by viewModels { photographViewModelFactory }

    private var _binding: FragmentPhotographBinding? = null
    private val binding get() = _binding!!

    private lateinit var executor: Executor
    private var imageCapture: ImageCapture? = null
    private val name =
        SimpleDateFormat(FILENAME_FORMAT, Locale.getDefault()).format(System.currentTimeMillis())

    private lateinit var newPhoto: Photo

    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.all { it }) {
                startCamera()
            } else {
                view?.let {
                    Snackbar.make(
                        it,
                        resources.getString(R.string.permission_is_not_granted),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotographBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        executor = context?.let { ContextCompat.getMainExecutor(it) }!!

        checkPermissions()

        binding.apply {
            takePhotoButton.setOnClickListener {
                takePhoto()
            }

            imagePreview.setOnClickListener {
                val bundle = Bundle().apply {
                    putString(ARG_URI, newPhoto.uri)
                    putString(ARG_DATE, SimpleDateFormat(DATE_PHOTO_FORMAT, Locale.getDefault()).format(newPhoto.date.time))
                }
                findNavController().navigate(R.id.action_photographFragment_to_photoFragment, bundle)
            }
        }
    }

    private fun checkPermissions() {
        val isAllGranted = PhotographFragment.REQUEST_PERMISSIONS.all { permission ->
            context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    permission
                )
            } == PackageManager.PERMISSION_GRANTED
        }
        if (isAllGranted) {
            startCamera()
        } else {
            launcher.launch(PhotographFragment.REQUEST_PERMISSIONS)
        }
    }


    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, TYPE)
        }

        val outputOption = ImageCapture.OutputFileOptions
            .Builder(
                requireContext().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()
        lifecycleScope.launch {
            imageCapture.takePicture(
                outputOption,
                executor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        Glide.with(requireActivity())
                            .load(outputFileResults.savedUri)
                            .circleCrop()
                            .into(binding.imagePreview)
                        newPhoto = Photo(outputFileResults.savedUri.toString())
                        viewModel.insertPhoto(newPhoto)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        exception.printStackTrace()
                    }
                }
            )
        }

    }

    private fun startCamera() {
        val cameraProviderFuture = context?.let { ProcessCameraProvider.getInstance(it) }
        cameraProviderFuture?.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.finderView.surfaceProvider)
            imageCapture = ImageCapture.Builder().build()
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                this,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                imageCapture
            )
        }, executor)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val REQUEST_PERMISSIONS: Array<String> = buildList {
            add(Manifest.permission.CAMERA)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
    }
}