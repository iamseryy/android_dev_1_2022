package com.attractions.presentation.viewmodel

import android.annotation.SuppressLint
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attractions.domain.SightUseCase
import com.attractions.entity.Sight
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val RECEIVING_INTERVAL_CURR_COORDINATES_MILLIS = 1_000L

class MapViewModel @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val sightUseCase: SightUseCase
) : ViewModel() {

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    private var _locationFlow = MutableStateFlow<Point?>(null)
    val locationFlow = _locationFlow.asStateFlow()

    private var _sightsFlow = MutableStateFlow<Sight?>(null)
    val sightsFlow = _sightsFlow.asStateFlow()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(position: LocationResult) {
            _locationFlow.value = Point(
                position.lastLocation!!.latitude,
                position.lastLocation!!.longitude)
        }
    }

    fun removeLocationUpdates() = locationClient.removeLocationUpdates(locationCallback)

    @SuppressLint("MissingPermission")
    fun startLocation() {
        val request = LocationRequest.create()
            .setInterval(RECEIVING_INTERVAL_CURR_COORDINATES_MILLIS)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)

        locationClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun findSights(point: Point, radius: Int, limit: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                sightUseCase.findSight(point.latitude, point.longitude, radius, limit)
            }.fold(
                onSuccess = {_sightsFlow.value = it},
                onFailure = {
                    Log.d("MapViewModel", it.message ?: "")
                    _error.send(it.message ?: "")
                }
            )
        }
    }
}