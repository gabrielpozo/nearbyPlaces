package com.gabriel.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabriel.domain.models.Place
import com.gabriel.domain.usecases.GetNearbyCafeListUseCase

/**
 * Created by Gabriel Pozo Guzman on 2019-12-01.
 */

class CafeViewModel(private val getNearbyCafeListUseCase: GetNearbyCafeListUseCase) :
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
        data class ErrorRetrievingPlaces(val error: Throwable) : UiModel()
        object GetLocation : UiModel()
        object RequestLocationPermission : UiModel()
    }

    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    fun getPlaceList(location: String) {
        _model.value = UiModel.Loading
        getNearbyCafeListUseCase.execute(::setContentUiModel, ::onErrorHandling, location)
    }

    fun onCoarsePermissionRequested() {
        _model.value = UiModel.GetLocation
    }

    private fun setContentUiModel(places: List<Place>) {
        _model.value = UiModel.Content(places)
    }

    private fun onErrorHandling(t: Throwable) {
        _model.value = UiModel.ErrorRetrievingPlaces(t)
    }

    private fun disposeActiveOperations() {
        getNearbyCafeListUseCase.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        disposeActiveOperations()
    }
}