package com.gabriel.nearbyplaces.di.modules

import com.gabriel.data.repository.PlacesRepositoryImpl
import com.gabriel.data.source.remote.PlacesRemoteSource
import com.gabriel.data.source.remote.PlacesRemoteSourceImpl
import com.gabriel.data.source.remote.retrofit.GoogleApiService
import com.gabriel.domain.repository.PlacesRepository
import com.gabriel.domain.usecases.GetNearbyRestaurantListUseCase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */
@Module
class NearbyPlacesModule{

    @Provides
    fun getGoogleApi(retrofit: Retrofit): GoogleApiService {
        return retrofit.create(GoogleApiService::class.java)
    }

    @Provides
    fun getRemoteSource(googleApiService: GoogleApiService): PlacesRemoteSource {
        return PlacesRemoteSourceImpl(googleApiService)
    }

    @Provides
    fun getPlacesRepository(placesRemoteSource: PlacesRemoteSource): PlacesRepository {
        return PlacesRepositoryImpl(placesRemoteSource)
    }

    @Provides
    fun getPlacesUseCase(placesRepository: PlacesRepository): GetNearbyRestaurantListUseCase {
        return GetNearbyRestaurantListUseCase(placesRepository)
    }

}
