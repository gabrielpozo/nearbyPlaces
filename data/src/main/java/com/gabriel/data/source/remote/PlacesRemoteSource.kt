package com.gabriel.data.source.remote

import com.gabriel.domain.models.Place
import io.reactivex.Single

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */
interface PlacesRemoteSource {
    fun getNearbyRestaurantList(): Single<List<Place>>
}