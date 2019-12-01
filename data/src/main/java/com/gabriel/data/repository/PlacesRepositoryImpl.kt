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
        const val cafe = "cafe"
        const val radius = 1000
    }

    override fun getNearbyRestaurantList(location: String): Single<List<Place>> {
        return placesRemoteSource.getNearbyRestaurantList(location, restaurantType, radius)
            .map(::sortPlaceListResult)
    }
}

fun sortPlaceListResult(places: List<Place>): List<Place> {
    return places.sortedWith(compareBy { it.distance })
}