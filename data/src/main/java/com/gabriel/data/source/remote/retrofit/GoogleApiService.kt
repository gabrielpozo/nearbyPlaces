package com.gabriel.data.source.remote.retrofit

import com.gabriel.data.BuildConfig
import com.gabriel.data.source.remote.responses.ResponsePlaces
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Gabriel Pozo Guzman on 2019-11-29.
 */

interface GoogleApiService {

    @GET("place/nearbysearch/json")
    fun getNearbyPlaces(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String,
        @Query("key") key: String = BuildConfig.google_api_key
    ): Single<ResponsePlaces>
}