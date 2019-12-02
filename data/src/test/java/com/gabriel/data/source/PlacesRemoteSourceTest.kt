package com.gabriel.data.source

import com.gabriel.data.BaseTest
import com.gabriel.data.source.remote.PlacesRemoteSource
import com.gabriel.data.source.remote.PlacesRemoteSourceImpl
import com.gabriel.data.source.remote.responses.*
import com.gabriel.data.source.remote.retrofit.GoogleApiService
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito

/**
 * Created by Gabriel Pozo Guzman on 2019-12-02.
 */

class PlacesRemoteSourceTest : BaseTest() {

    @Mock
    private lateinit var googleApiService: GoogleApiService

    private lateinit var placesRemoteSource: PlacesRemoteSource

    @Before
    fun setup() {
        placesRemoteSource = PlacesRemoteSourceImpl(googleApiService, "")
    }

    @Test
    fun `get places from API successfully`() {
        val currentLocation = ""
        val radius = 0
        val type = "type"
        val coordinatesDto = CoordinatesDto(0.0, 0.0)
        val locationDto = LocationDto(coordinatesDto)
        val openingHoursDto = OpeningHoursDto(true)
        val placeDTO = PlaceDto("name", "rating", locationDto, openingHoursDto)

        val responseDto = mock<ResponsePlaces> {
            on(it.results).thenReturn(listOf(placeDTO))
        }

        Mockito.`when`(
            googleApiService.getNearbyPlaces(
                anyString(),
                anyInt(),
                anyString(),
                anyString()
            )
        )
            .thenReturn(Single.just(responseDto))

        val testObserver =
            placesRemoteSource.getNearbyPlaceList(currentLocation, type, radius).test()

        Mockito.verify(googleApiService).getNearbyPlaces(
            anyString(),
            anyInt(),
            anyString(),
            anyString()
        )

        testObserver.assertNotComplete()
    }


    @Test
    fun `error getting places from API`() {
        val currentLocation = ""
        val radius = 0
        val type = "type"
        val throwable = Throwable("Error getting places from API")
        Mockito.`when`(
            googleApiService.getNearbyPlaces(
                anyString(),
                anyInt(),
                anyString(),
                anyString()
            )
        ).thenReturn(Single.error(throwable))

        val testObserver =
            placesRemoteSource.getNearbyPlaceList(currentLocation, type, radius).test()

        Mockito.verify(googleApiService).getNearbyPlaces(
            anyString(),
            anyInt(),
            anyString(),
            anyString()
        )

        testObserver.assertError(throwable)
    }
}