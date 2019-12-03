package com.gabriel.data.source.remote.responses

import com.google.gson.annotations.SerializedName

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */
data class PlaceDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("geometry")
    val geometry: LocationDto,
    @SerializedName("opening_hours")
    val openingHours: OpeningHoursDto?,
    var distance: Float = 0.0f
)

data class LocationDto(
    @SerializedName("location")
    val location: CoordinatesDto
)

data class CoordinatesDto(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)

data class OpeningHoursDto(@SerializedName("open_now") val open_now: Boolean)