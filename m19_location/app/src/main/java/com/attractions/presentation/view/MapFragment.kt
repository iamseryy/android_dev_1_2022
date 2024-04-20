package com.attractions.presentation.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.attractions.R
import com.attractions.databinding.FragmentMapBinding
import com.attractions.presentation.viewmodel.MapViewModel
import com.attractions.presentation.viewmodel.MapViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Circle
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment : Fragment(), CameraListener {

    @Inject
    lateinit var mapViewModelFactory: MapViewModelFactory
    private val viewModel: MapViewModel by viewModels { mapViewModelFactory }

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private var isPermissionGranted = false
    private lateinit var currentUserPoint: Point
    private lateinit var currentPoint: Point
    private lateinit var userLocationLayer: UserLocationLayer

    private var currentLatitude = 0.0
    private var currentLongitude = 0.0
    private var currentZoom = 0f

    private lateinit var mapView: MapView
    private lateinit var mapKit: MapKit
    private lateinit var mapObjectCollection: MapObjectCollection

    private val placeMarkTapListener = MapObjectTapListener { mapObject, _ ->
        view?.let { Snackbar.make(it, "${mapObject.userData}", Snackbar.LENGTH_SHORT).show() }
        true
    }


    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.all { it }) {
                isPermissionGranted = true
                prepareMapForLocationPoints()
            } else {
                isPermissionGranted = false
                view?.let {
                    Snackbar.make(
                        it,
                        resources.getString(R.string.permission_is_not_granted),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

    private fun prepareMapForLocationPoints() {
        userLocationLayer = mapKit.createUserLocationLayer(binding.mapview.mapWindow)
        userLocationLayer.isVisible = true
        mapView.mapWindow.map.addCameraListener(this)

        viewModel.startLocation()

        viewModel.sightsFlow.onEach {
            val imageProvider =
                ImageProvider.fromResource(requireContext(), R.drawable.point)

            mapObjectCollection = mapView.mapWindow.map.mapObjects.addCollection()
            it?.features?.forEach { point ->
                mapObjectCollection.addPlacemark().apply {
                    geometry = Point(
                        point.geometry.coordinates[1],
                        point.geometry.coordinates[0]
                    )
                    userData = point.properties.name
                    setIcon(imageProvider)
                    addTapListener(placeMarkTapListener)
                }
            }

        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(requireContext())
        mapKit = MapKitFactory.getInstance()
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

        mapView = binding.mapview

        checkPermissions()

        binding.apply {
            increaseButton.setOnClickListener {
                zoomIn()
            }

            decreaseButton.setOnClickListener {
                zoomOut()
            }

            currentLocationButton.setOnClickListener {
                if (isPermissionGranted) {
                    mapView.map.move(
                        CameraPosition(
                            currentUserPoint,
                            currentZoom,
                            USER_CURRENT_AZIMUTH,
                            USER_CURRENT_TILT),
                        Animation(Animation.Type.LINEAR, ANIM_DURATION),
                        null
                    )
                } else {
                    Snackbar.make(
                        view,
                        resources.getString(R.string.permission_is_not_granted),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

        if (savedInstanceState != null) {
            currentLatitude = savedInstanceState.getDouble(OUT_STATE_LATITUDE)
            currentLongitude = savedInstanceState.getDouble(OUT_STATE_LONGITUDE)
            currentZoom = savedInstanceState.getFloat(OUT_STATE_ZOOM)
            currentPoint = Point(currentLatitude, currentLongitude)


            mapView.map.move(
                CameraPosition(
                    currentPoint,
                    currentZoom,
                    USER_CURRENT_AZIMUTH,
                    USER_CURRENT_TILT
                ),
                Animation(Animation.Type.LINEAR, 0.0F),
                null
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.locationFlow.collect {
                if (it != null) {
                    if (!this@MapFragment::currentUserPoint.isInitialized
                        && !this@MapFragment::currentPoint.isInitialized
                    ) {
                        currentUserPoint = it
                        mapView.map.move(
                            CameraPosition(
                                currentUserPoint,
                                USER_CURRENT_ZOOM,
                                USER_CURRENT_AZIMUTH,
                                USER_CURRENT_TILT
                            ),
                            Animation(Animation.Type.LINEAR, ANIM_DURATION),
                            null
                        )
                        viewModel.findSights(it, RADIUS, LIMIT)
                    }
                    currentUserPoint = it
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collect {
                Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun zoomIn() {
        with(mapView.mapWindow.map.cameraPosition) {
            mapView.mapWindow.map.move(
                CameraPosition(target, zoom + ZOOM_STEP, azimuth, tilt),
                Animation(Animation.Type.LINEAR, ANIM_DURATION),
                null,
            )
        }
    }

    private fun zoomOut() {
        with(mapView.mapWindow.map.cameraPosition) {
            mapView.mapWindow.map.move(
                CameraPosition(target, zoom - ZOOM_STEP, azimuth, tilt),
                Animation(Animation.Type.LINEAR, ANIM_DURATION),
                null,
            )
        }
    }

    private fun checkPermissions() {
        if (REQUEST_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission( requireContext(), permission) == PackageManager.PERMISSION_GRANTED
        }) {
            isPermissionGranted = true
            prepareMapForLocationPoints()
        } else {
            isPermissionGranted = false
            launcher.launch(REQUEST_PERMISSIONS)
        }
    }

    override fun onCameraPositionChanged(
        p0: Map,
        p1: CameraPosition,
        p2: CameraUpdateReason,
        p3: Boolean
    ) {
        if (!p3) {
            currentLatitude = p1.target.latitude
            currentLongitude = p1.target.longitude
            currentZoom = p1.zoom
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble(OUT_STATE_LATITUDE, currentLatitude)
        outState.putDouble(OUT_STATE_LONGITUDE, currentLongitude)
        outState.putFloat(OUT_STATE_ZOOM, currentZoom)
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }


    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        viewModel.removeLocationUpdates()
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val REQUEST_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        private const val ZOOM_STEP = 1f
        private const val USER_CURRENT_ZOOM = 16.0f
        private const val USER_CURRENT_AZIMUTH = 0.0f
        private const val USER_CURRENT_TILT = 0.0f
        private const val ANIM_DURATION = 1f
        private const val OUT_STATE_LATITUDE = "latitude"
        private const val OUT_STATE_LONGITUDE = "longitude"
        private const val OUT_STATE_ZOOM = "zoom"
        private const val RADIUS = 1000
        private const val LIMIT = 500
    }

}