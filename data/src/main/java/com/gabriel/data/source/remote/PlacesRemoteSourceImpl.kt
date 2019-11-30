package com.gabriel.data.source.remote

import com.gabriel.data.mapper.mapRemotePlaceToDomain
import com.gabriel.data.source.remote.retrofit.GoogleApiService
import com.gabriel.domain.models.Place
import io.reactivex.Single

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */
class PlacesRemoteSourceImpl(private val googleApiService: GoogleApiService) : PlacesRemoteSource {
    override fun getNearbyRestaurantList(): Single<List<Place>> {
        return googleApiService.getNearbyPlaces(
            "52.375985, 4.881233",
            1000,
            "restaurant",
            "AIzaSyBpN-RUZ-2i_HkLk331S4aiOLKKNmHRbC4"
        ).map { apiResponse ->
            apiResponse.results.map(mapRemotePlaceToDomain)
        }
    }
}