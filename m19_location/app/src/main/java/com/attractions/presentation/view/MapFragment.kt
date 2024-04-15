package com.attractions.presentation.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.attractions.R
import com.attractions.databinding.FragmentMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.material.snackbar.Snackbar
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.CameraPosition
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment : Fragment() {
    private lateinit var fusedClient: FusedLocationProviderClient

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapView: com.yandex.mapkit.mapview.MapView


    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.all { it }) {
                //startLocations()
                onMapReady()
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



    private var locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {

        }
    }

    private fun startLocations() {
        val request = LocationRequest.create()
            .setInterval(1_000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.initialize(requireContext())

        fusedClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermissions()
        binding.apply {
            mapView = this.mapview

            increaseButton.setOnClickListener {
                zoomIn()
            }

            decreaseButton.setOnClickListener {
                zoomOut()
            }

            currentLocationButton.setOnClickListener {

            }
        }
    }

    private fun zoomIn() {
        with(mapView.mapWindow.map.cameraPosition) {
            mapView.mapWindow.map.move(
                CameraPosition(target, zoom + ZOOM_STEP, azimuth, tilt),
                Animation(Animation.Type.LINEAR, 1f),
                null,
            )
        }
    }

    private fun zoomOut() {
        with(mapView.mapWindow.map.cameraPosition) {
            mapView.mapWindow.map.move(
                CameraPosition(target, zoom - ZOOM_STEP, azimuth, tilt),
                Animation(Animation.Type.LINEAR, 1f),
                null,
            )
        }
    }

    private fun onMapReady() {
        val mapKit = MapKitFactory.getInstance()

    }


    private fun checkPermissions() {
        if (REQUEST_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission( requireContext(), permission) == PackageManager.PERMISSION_GRANTED
        }) {
            startLocations()
        } else {
            launcher.launch(REQUEST_PERMISSIONS)
        }

    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }


    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()

        super.onStop()

        fusedClient.removeLocationUpdates(locationCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val REQUEST_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        private const val ZOOM_STEP = 1f
    }
}