package com.gabriel.domain.usecases

import com.gabriel.domain.models.Place
import com.gabriel.domain.repository.PlacesRepository
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*

/**
 * Created by Gabriel Pozo Guzman on 2019-12-02.
 */

class GetNearbyRestaurantListUseCaseTest: BaseTest() {

    @Mock
    private lateinit var placesRepository: PlacesRepository

    private lateinit var getNearbyRestaurantListUseCase: GetNearbyRestaurantListUseCase

    @Before
    fun setup() {
        getNearbyRestaurantListUseCase =
            GetNearbyRestaurantListUseCase(
                placesRepository
            )
    }

    @Test
    fun `execute get restaurant list use case successfully`() {
        val favoriteUsers = mock<List<Place>>()
        `when`(placesRepository.getNearbyRestaurantList(anyString())).thenReturn(Single.just(favoriteUsers))

        val testObserver = getNearbyRestaurantListUseCase.useCaseExecution("location").test()

        verify(placesRepository).getNearbyRestaurantList(ArgumentMatchers.anyString())
        testObserver.assertComplete()
    }

    @Test
    fun `execute get restaurant list use case returning an error`() {
        val throwable = Throwable("Error getting favorite users list")
        `when`(placesRepository.getNearbyRestaurantList(anyString())).thenReturn(Single.error(throwable))

        val testObserver = getNearbyRestaurantListUseCase.useCaseExecution("location").test()

        verify(placesRepository).getNearbyRestaurantList(ArgumentMatchers.anyString())
        testObserver.assertError(throwable)
    }
}