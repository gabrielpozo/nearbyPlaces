package com.gabriel.data.source.remote


import com.gabriel.data.mapper.mapRemotePlaceToDomain
import com.gabriel.data.source.remote.retrofit.GoogleApiService
import com.gabriel.data.source.remote.utils.setDistanceToPlace
import com.gabriel.domain.models.Place
import io.reactivex.Single

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */

class PlacesRemoteSourceImpl(
    private val googleApiService: GoogleApiService
) : PlacesRemoteSource {

    override fun getNearbyPlaceList(
        currentLocation: String,
        type: String,
        radius: Int
    ): Single<List<Place>> {
        return googleApiService.getNearbyPlaces(
            currentLocation,
            radius,
            type
        ).map { apiResponse ->
            apiResponse.results.setDistanceToPlace(currentLocation).map(mapRemotePlaceToDomain)
        }
    }
}





