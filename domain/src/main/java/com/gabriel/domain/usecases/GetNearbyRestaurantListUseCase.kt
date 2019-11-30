package com.gabriel.domain.usecases

import com.gabriel.domain.base.SingleUseCase
import com.gabriel.domain.models.Place
import com.gabriel.domain.repository.PlacesRepository
import io.reactivex.Single

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */
class GetNearbyRestaurantListUseCase(private val placesRepository: PlacesRepository) : SingleUseCase<List<Place>, Unit>() {

    override fun useCaseExecution(params: Unit): Single<List<Place>> {
        return placesRepository.getNearbyRestaurantList()
    }
}