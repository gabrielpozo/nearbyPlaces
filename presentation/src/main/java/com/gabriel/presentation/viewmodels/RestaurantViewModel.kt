package com.gabriel.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabriel.domain.models.Place
import com.gabriel.domain.usecases.GetNearbyRestaurantListUseCase

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */
class RestaurantViewModel(private val getNearbyRestaurantListUseCase: GetNearbyRestaurantListUseCase) :
    ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        data class Content(val places: List<Place>) : UiModel()
        object GetLocation : UiModel()
        object RequestLocationPermission : UiModel()
    }

    private fun refresh() {
        Log.d("Gabriel","refresh")
        _model.value = UiModel.RequestLocationPermission
    }

    fun getPlaceList(location: String) {
        _model.value = UiModel.Loading
        getNearbyRestaurantListUseCase.execute(::setContentUiModel, ::onErrorHandling, location)
    }

    fun onCoarsePermissionRequested() {
        _model.value = UiModel.GetLocation
    }

    private fun setContentUiModel(places: List<Place>) {
        _model.value = UiModel.Content(places)
    }

    private fun onErrorHandling(t: Throwable) {

    }

    override fun onCleared() {
        super.onCleared()
    }


}