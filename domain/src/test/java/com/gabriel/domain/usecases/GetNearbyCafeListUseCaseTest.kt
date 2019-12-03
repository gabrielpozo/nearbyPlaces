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

class GetNearbyCafeListUseCaseTest : BaseTest() {

    @Mock
    private lateinit var placesRepository: PlacesRepository

    private lateinit var getNearbyCafeListUseCaseTest: GetNearbyCafeListUseCase

    @Before
    fun setup() {
        getNearbyCafeListUseCaseTest =
            GetNearbyCafeListUseCase(
                placesRepository
            )
    }

    @Test
    fun `execute cafe list use case successfully`() {
        val location = "location"
        val cafes = mock<List<Place>>()
        Mockito.`when`(placesRepository.getNearbyCafeList(Mockito.anyString())).thenReturn(
            Single.just(cafes))

        val testObserver = getNearbyCafeListUseCaseTest.useCaseExecution(location).test()

        Mockito.verify(placesRepository).getNearbyCafeList(ArgumentMatchers.anyString())
        testObserver.assertComplete()
    }

    @Test
    fun `execute get cafe list use case returning an error`() {
        val location = "location"
        val throwable = Throwable("Error getting cafe list")
        Mockito.`when`(placesRepository.getNearbyCafeList(Mockito.anyString())).thenReturn(
            Single.error(throwable))

        val testObserver = getNearbyCafeListUseCaseTest.useCaseExecution(location).test()

        Mockito.verify(placesRepository).getNearbyCafeList(ArgumentMatchers.anyString())
        testObserver.assertError(throwable)
    }
}