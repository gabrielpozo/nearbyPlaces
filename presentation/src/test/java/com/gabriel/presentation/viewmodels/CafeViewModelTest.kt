package com.gabriel.presentation.viewmodels

import androidx.lifecycle.Observer
import com.gabriel.domain.models.Place
import com.gabriel.domain.repository.PlacesRepository
import com.gabriel.domain.usecases.GetNearbyCafeListUseCase
import com.gabriel.presentation.viewmodels.CafeViewModel.*
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Gabriel Pozo Guzman on 2019-12-02.
 */

@RunWith(MockitoJUnitRunner::class)
class CafeViewModelTest : BaseTest() {

    @Mock
    private lateinit var placesRepository: PlacesRepository
    @Mock
    lateinit var observer: Observer<UiModel>

    private lateinit var getNearbyCafeUseCase: GetNearbyCafeListUseCase
    private lateinit var vm: CafeViewModel

    private lateinit var location: String

    @Before
    fun setUp() {
        getNearbyCafeUseCase = GetNearbyCafeListUseCase(placesRepository)
        vm = CafeViewModel(getNearbyCafeUseCase)
        location = "location"
    }

    @Test
    fun `observing liveData launches location permission request`() {
        vm.model.observeForever(observer)

        Mockito.verify(observer).onChanged(UiModel.RequestLocationPermission)
    }

    @Test
    fun `after requesting the permission, request the location`() {
        vm.model.observeForever(observer)

        vm.onCoarsePermissionRequested()

        Mockito.verify(observer).onChanged(UiModel.GetLocation)
    }

    @Test
    fun `after requesting the permission, loading is shown`() {
        val places = mock<List<Place>>()
        vm.model.observeForever(observer)
        Mockito.`when`(placesRepository.getNearbyCafeList(ArgumentMatchers.anyString())).thenReturn(
            Single.just(places)
        )

        vm.getPlaceList(location)

        Mockito.verify(observer).onChanged(UiModel.Loading)
    }

    @Test
    fun `after requesting the permission, getNearbyRestaurantList is called successfully`() {
        val places = mock<List<Place>>()
        vm.model.observeForever(observer)
        Mockito.`when`(placesRepository.getNearbyCafeList(ArgumentMatchers.anyString()))
            .thenReturn(Single.just(places))

        vm.getPlaceList("")

        Mockito.verify(placesRepository).getNearbyCafeList(ArgumentMatchers.anyString())
        Mockito.verify(observer).onChanged(UiModel.Content(places))
    }

    @Test
    fun `after requesting the permission, getNearbyRestaurantList is called error`() {
        val throwable = Throwable("Error retrieving restaurants")
        vm.model.observeForever(observer)
        Mockito.`when`(placesRepository.getNearbyCafeList(ArgumentMatchers.anyString()))
            .thenReturn(Single.error(throwable))

        vm.getPlaceList("location")

        Mockito.verify(placesRepository).getNearbyCafeList(ArgumentMatchers.anyString())
        Mockito.verify(observer).onChanged(UiModel.ErrorRetrievingPlaces(throwable))
    }
}
