package com.gabriel.domain.repository

import com.gabriel.domain.models.Place
import io.reactivex.Single

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */
interface PlacesRepository {
    fun getNearbyRestaurantList(location: String): Single<List<Place>>
}