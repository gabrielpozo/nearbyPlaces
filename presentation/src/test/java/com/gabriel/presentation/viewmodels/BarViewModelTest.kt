package com.gabriel.presentation.viewmodels

import androidx.lifecycle.Observer
import com.gabriel.domain.models.Place
import com.gabriel.domain.repository.PlacesRepository
import com.gabriel.domain.usecases.GetNearbyBarListUseCase
import com.gabriel.presentation.viewmodels.BarViewModel.*
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
class BarViewModelTest : BaseTest() {

    @Mock
    private lateinit var placesRepository: PlacesRepository
    @Mock
    lateinit var observer: Observer<UiModel>

    private lateinit var getNearbyBarUseCase: GetNearbyBarListUseCase
    private lateinit var vm: BarViewModel

    private lateinit var location: String

    @Before
    fun setUp() {
        getNearbyBarUseCase = GetNearbyBarListUseCase(placesRepository)
        vm = BarViewModel(getNearbyBarUseCase)
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
        Mockito.`when`(placesRepository.getNearbyBarList(ArgumentMatchers.anyString())).thenReturn(
            Single.just(places)
        )

        vm.getPlaceList(location)

        Mockito.verify(observer).onChanged(UiModel.Loading)
    }
}
