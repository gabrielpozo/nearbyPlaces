package com.gabriel.data.mapper

import com.gabriel.data.source.remote.responses.PlaceDto
import com.gabriel.domain.models.Place

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */
val mapRemotePlaceToDomain: (PlaceDto) -> Place = { place ->
    Place(
        place.name,
        place.rating,
        place.distance.toInt(),
        place.openingHours?.let { if (it.open_now) "Open now" else "Closed" } ?: "No Opening Hours"
    )
}
