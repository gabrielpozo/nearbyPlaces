package com.gabriel.nearbyplaces.di.modules


import android.Manifest.permission.*
import android.app.Activity
import com.gabriel.data.source.remote.location.PermissionChecker
import com.gabriel.data.repository.PlacesRepositoryImpl
import com.gabriel.data.source.remote.location.RegionRepository
import com.gabriel.data.source.remote.PlacesRemoteSource
import com.gabriel.data.source.remote.PlacesRemoteSourceImpl
import com.gabriel.data.source.remote.location.LocationDataSource
import com.gabriel.data.source.remote.retrofit.GoogleApiService
import com.gabriel.domain.repository.PlacesRepository
import com.gabriel.domain.usecases.GetNearbyBarListUseCase
import com.gabriel.domain.usecases.GetNearbyCafeListUseCase
import com.gabriel.domain.usecases.GetNearbyRestaurantListUseCase
import com.gabriel.nearbyplaces.R
import com.gabriel.nearbyplaces.utils.PermissionRequester
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */

@Module
class NearbyPlacesModule(private val context: Activity) {

    @Provides
    fun getGoogleApi(retrofit: Retrofit): GoogleApiService {
        return retrofit.create(GoogleApiService::class.java)
    }

    @Provides
    fun getRemoteSource(googleApiService: GoogleApiService): PlacesRemoteSource {
        return PlacesRemoteSourceImpl(googleApiService)
    }

    @Provides
    fun getRegionRepository(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ): RegionRepository {
        return RegionRepository(
            locationDataSource,
            permissionChecker
        )
    }

    @Provides
    fun getPlacesRepository(
        placesRemoteSource: PlacesRemoteSource
    ): PlacesRepository {
        return PlacesRepositoryImpl(placesRemoteSource)
    }

    @Provides
    fun getRestaurantListUseCase(placesRepository: PlacesRepository): GetNearbyRestaurantListUseCase {
        return GetNearbyRestaurantListUseCase(placesRepository)
    }

    @Provides
    fun getBarListUseCase(placesRepository: PlacesRepository): GetNearbyBarListUseCase {
        return GetNearbyBarListUseCase(placesRepository)
    }

    @Provides
    fun getCafeListUseCase(placesRepository: PlacesRepository): GetNearbyCafeListUseCase {
        return GetNearbyCafeListUseCase(placesRepository)
    }

    @Provides
    fun getPermissionRequester(): PermissionRequester {
        return PermissionRequester(context, ACCESS_FINE_LOCATION)
    }
}
