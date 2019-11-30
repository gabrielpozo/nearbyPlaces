package com.gabriel.data.source.remote

import com.gabriel.data.mapper.mapRemotePlaceToDomain
import com.gabriel.data.source.remote.retrofit.GoogleApiService
import com.gabriel.domain.models.Place
import io.reactivex.Single

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */
class PlacesRemoteSourceImpl(private val googleApiService: GoogleApiService) : PlacesRemoteSource {

    override fun getNearbyRestaurantList(
        apiKey: String,
        currentLocation: String
    ): Single<List<Place>> {
        return googleApiService.getNearbyPlaces(
            currentLocation, 1500,
            "restaurant",
            apiKey
        ).map { apiResponse ->
            apiResponse.results.map(mapRemotePlaceToDomain)
        }
    }
}