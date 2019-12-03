package com.gabriel.domain.usecases

import com.gabriel.domain.models.Place
import com.gabriel.domain.repository.PlacesRepository
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito

/**
 * Created by Gabriel Pozo Guzman on 2019-12-02.
 */

class GetNearbyBarListUseCaseTest : BaseTest() {

    @Mock
    private lateinit var placesRepository: PlacesRepository

    private lateinit var getNearbyBarListUseCase: GetNearbyBarListUseCase

    @Before
    fun setup() {
        getNearbyBarListUseCase =
            GetNearbyBarListUseCase(
                placesRepository
            )
    }

    @Test
    fun `execute get bar list use case successfully`() {
        val location = "location"
        val favoriteUsers = mock<List<Place>>()
        Mockito.`when`(placesRepository.getNearbyBarList(Mockito.anyString())).thenReturn(
            Single.just(favoriteUsers)
        )

        val testObserver = getNearbyBarListUseCase.useCaseExecution(location).test()

        Mockito.verify(placesRepository).getNearbyBarList(ArgumentMatchers.anyString())
        testObserver.assertComplete()
    }

    @Test
    fun `execute get bar list use case returning an error`() {
        val location = "location"
        val throwable = Throwable("Error getting favorite users list")
        Mockito.`when`(placesRepository.getNearbyBarList(Mockito.anyString())).thenReturn(
            Single.error(throwable)
        )

        val testObserver = getNearbyBarListUseCase.useCaseExecution(location).test()

        Mockito.verify(placesRepository).getNearbyBarList(ArgumentMatchers.anyString())
        testObserver.assertError(throwable)
    }
}