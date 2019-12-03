package com.gabriel.presentation.di.modules

import androidx.lifecycle.ViewModel
import com.gabriel.domain.usecases.GetNearbyBarListUseCase
import com.gabriel.domain.usecases.GetNearbyCafeListUseCase
import com.gabriel.domain.usecases.GetNearbyRestaurantListUseCase
import com.gabriel.presentation.di.ViewModelProviderFactory
import com.gabriel.presentation.viewmodels.BarViewModel
import com.gabriel.presentation.viewmodels.CafeViewModel
import com.gabriel.presentation.viewmodels.RestaurantViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import kotlin.reflect.KClass

/**
 * Created by Gabriel Pozo Guzman on 2019-11-07.
 */

@Module
class ViewModelModule {

    @Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
    )
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    @MapKey
    internal annotation class ViewModelKey(val value: KClass<out ViewModel>)


    @Provides
    fun viewModelFactory(providerMap: MutableMap<Class<out ViewModel>, Provider<ViewModel>>): ViewModelProviderFactory {
        return ViewModelProviderFactory(providerMap)
    }

    @Provides
    @IntoMap
    @ViewModelKey(RestaurantViewModel::class)
    fun restaurantViewModel(
        getNearbyRestaurantListUseCase: GetNearbyRestaurantListUseCase
    ): ViewModel {
        return RestaurantViewModel(getNearbyRestaurantListUseCase)
    }

    @Provides
    @IntoMap
    @ViewModelKey(BarViewModel::class)
    fun barViewModel(
        getNearbyBarListUseCase: GetNearbyBarListUseCase
    ): ViewModel {
        return BarViewModel(getNearbyBarListUseCase)
    }

    @Provides
    @IntoMap
    @ViewModelKey(CafeViewModel::class)
    fun cafeViewModel(
        getNearbyCafeListUseCase: GetNearbyCafeListUseCase
    ): ViewModel {
        return CafeViewModel(getNearbyCafeListUseCase)
    }
}