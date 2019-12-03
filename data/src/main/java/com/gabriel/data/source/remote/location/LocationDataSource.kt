package com.gabriel.data.source.remote.location

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */
interface LocationDataSource {
    fun findCurrentLocation(location: (String) -> Unit)
}