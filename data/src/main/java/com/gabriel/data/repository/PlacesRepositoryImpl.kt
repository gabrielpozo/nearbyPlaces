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

    override fun getNearbyRestaurantList(location: String): Single<List<Place>> {
        return placesRemoteSource.getNearbyRestaurantList(
            "AIzaSyAPiA_DK754K3yyKlEaFSLyv-Lxi3-AYYY", location
        )
    }
}