package com.gabriel.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider


/**
 * Created by Gabriel Pozo Guzman on 2019-11-07.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelProviderFactory(private val providerMap: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return providerMap[modelClass]?.get() as T
    }
}