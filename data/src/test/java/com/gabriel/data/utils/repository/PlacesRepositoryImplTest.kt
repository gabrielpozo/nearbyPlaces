package com.gabriel.data.utils.repository

import com.gabriel.data.BaseTest
import com.gabriel.data.repository.PlacesRepositoryImpl
import com.gabriel.data.source.remote.PlacesRemoteSource
import com.gabriel.domain.models.Place
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Gabriel Pozo Guzman on 2019-12-02.
 */

@RunWith(MockitoJUnitRunner::class)
class PlacesRepositoryImplTest : BaseTest() {

    @Mock
    private lateinit var placesRemoteSource: PlacesRemoteSource

    private lateinit var placesRepository: PlacesRepositoryImpl
    private lateinit var place: Place

    @Before
    fun setup() {
        placesRepository = PlacesRepositoryImpl(placesRemoteSource)
        place = Place("name", "4.9", 500, "Open now")
    }

    @Test
    fun `get restaurants nearby list successfully`() {
        val places = mock<List<Place>>()
        Mockito.`when`(placesRemoteSource.getNearbyPlaceList(anyString(), anyString(), anyInt()))
            .thenReturn(Single.just(places))

        val testObserver = placesRepository.getNearbyRestaurantList("").test()

        verify(placesRemoteSource).getNearbyPlaceList(anyString(), anyString(), anyInt())
        testObserver.assertComplete()
    }

    @Test
    fun `error retrieving list of restaurants`() {
        val throwable = Throwable("Error getting users")
        Mockito.`when`(placesRemoteSource.getNearbyPlaceList(anyString(), anyString(), anyInt()))
            .thenReturn(Single.error(throwable))

        val testObserver = placesRepository.getNearbyRestaurantList("").test()

        verify(placesRemoteSource).getNearbyPlaceList(anyString(), anyString(), anyInt())
        testObserver.assertError(throwable)
    }

    @Test
    fun `get cafes nearby list successfully`() {
        val places = mock<List<Place>>()
        Mockito.`when`(placesRemoteSource.getNearbyPlaceList(anyString(), anyString(), anyInt()))
            .thenReturn(Single.just(places))

        val testObserver = placesRepository.getNearbyCafeList("").test()

        verify(placesRemoteSource).getNearbyPlaceList(anyString(), anyString(), anyInt())
        testObserver.assertComplete()
    }

    @Test
    fun `error retrieving list of cafes`() {
        val throwable = Throwable("Error getting users")
        Mockito.`when`(placesRemoteSource.getNearbyPlaceList(anyString(), anyString(), anyInt()))
            .thenReturn(Single.error(throwable))

        val testObserver = placesRepository.getNearbyCafeList("").test()

        verify(placesRemoteSource).getNearbyPlaceList(anyString(), anyString(), anyInt())
        testObserver.assertError(throwable)
    }

    @Test
    fun `get bars nearby list successfully`() {
        val places = mock<List<Place>>()
        Mockito.`when`(placesRemoteSource.getNearbyPlaceList(anyString(), anyString(), anyInt()))
            .thenReturn(Single.just(places))

        val testObserver = placesRepository.getNearbyBarList("").test()

        verify(placesRemoteSource).getNearbyPlaceList(anyString(), anyString(), anyInt())
        testObserver.assertComplete()
    }

    @Test
    fun `error retrieving list of bars`() {
        val throwable = Throwable("Error getting users")
        Mockito.`when`(placesRemoteSource.getNearbyPlaceList(anyString(), anyString(), anyInt()))
            .thenReturn(Single.error(throwable))

        val testObserver = placesRepository.getNearbyBarList("").test()

        verify(placesRemoteSource).getNearbyPlaceList(anyString(), anyString(), anyInt())
        testObserver.assertError(throwable)
    }
}
