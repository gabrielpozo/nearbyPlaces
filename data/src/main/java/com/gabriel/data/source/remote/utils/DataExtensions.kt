package com.gabriel.data.source.remote.utils

import android.location.Location
import com.gabriel.data.source.remote.responses.PlaceDto

/**
 * Created by Gabriel Pozo Guzman on 2019-12-01.
 */
fun List<PlaceDto>.setDistanceToPlace(currentLocation: String): List<PlaceDto> {
    val chain = currentLocation.split(" ")
    val latitudeDevice = chain[0].dropLast(1)
    val longitudeDevice = chain[1]
    forEach { placeDto ->
        val distance = distance(
            latitudeDevice.toDouble(),
            longitudeDevice.toDouble(),
            placeDto.geometry.location.lat,
            placeDto.geometry.location.lng
        )
        placeDto.distance = distance
    }
    return this
}

fun distance(
    currentLatitude: Double,
    currentLongitude: Double,
    originLat: Double,
    originLon: Double
): Float {
    val results = FloatArray(1)
    Location.distanceBetween(currentLatitude, currentLongitude, originLat, originLon, results)
    return results[0]
}