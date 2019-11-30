package com.gabriel.data.source.remote.location

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import com.google.android.gms.location.LocationServices

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */
class PlayServicesLocationDataSource(application: Application) :
    LocationDataSource {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    @SuppressLint("MissingPermission")
    override fun findCurrentLocation(location: (String) -> Unit) {
        fusedLocationClient.lastLocation
            .addOnCompleteListener {
                location(it.result.toCoordinates() ?: " Unknown Location")
            }
    }

    private fun Location?.toCoordinates() = this?.let { "$latitude, $longitude" }
}

