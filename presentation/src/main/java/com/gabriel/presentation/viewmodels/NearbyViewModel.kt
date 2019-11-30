package com.gabriel.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabriel.domain.models.Place
import com.gabriel.domain.usecases.GetNearbyRestaurantListUseCase

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */
class NearbyViewModel(private val getNearbyRestaurantListUseCase: GetNearbyRestaurantListUseCase) : ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        data class Content(val places: List<Place>) : UiModel()
        data class Navigation(val places: Place) : UiModel()
        object RequestLocationPermission : UiModel()
    }


    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    fun onCoarsePermissionRequested() {
        _model.value = UiModel.Loading
        //_model.value = UiModel.Content(getPopularMovies.invoke())

    }

    override fun onCleared() {
        super.onCleared()
    }


}