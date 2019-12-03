package com.gabriel.data.repository

import com.gabriel.data.source.remote.PlacesRemoteSource
import com.gabriel.domain.models.Place
import com.gabriel.domain.repository.PlacesRepository
import io.reactivex.Single

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */

class PlacesRepositoryImpl(
    private val placesRemoteSource: PlacesRemoteSource
) : PlacesRepository {

    companion object {
        const val restaurantType = "restaurant"
        const val barType = "bar"
        const val cafeType = "cafe"
        const val radius = 1500
    }

    override fun getNearbyRestaurantList(location: String): Single<List<Place>> {
        return placesRemoteSource.getNearbyPlaceList(location, restaurantType, radius)
            .map(::sortPlaceListResult)
    }

    override fun getNearbyBarList(location: String): Single<List<Place>> {
        return placesRemoteSource.getNearbyPlaceList(location, barType, radius)
            .map(::sortPlaceListResult)
    }

    override fun getNearbyCafeList(location: String): Single<List<Place>> {
        return placesRemoteSource.getNearbyPlaceList(location, cafeType, radius)
            .map(::sortPlaceListResult)
    }
}

fun sortPlaceListResult(places: List<Place>): List<Place> {
    return places.sortedWith(compareBy { it.distance })
}