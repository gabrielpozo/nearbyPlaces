package com.gabriel.nearbyplaces.ui

import androidx.appcompat.app.AppCompatActivity
import com.gabriel.nearbyplaces.NearbyApplication
import com.gabriel.nearbyplaces.di.ApplicationComponent
import com.gabriel.nearbyplaces.di.modules.NearbyPlacesModule
import com.gabriel.nearbyplaces.di.subcomponents.PresentationComponent

/**
 * Created by Gabriel Pozo Guzman on 2019-11-29.
 */
abstract class BaseActivity : AppCompatActivity() {

    private var mIsInjectorUsed: Boolean = false

    protected fun getPresentationComponent(): PresentationComponent {
        if (mIsInjectorUsed) {
            throw RuntimeException("there is no need to use injector more than once")
        }
        mIsInjectorUsed = true
        return getApplicationComponent().presentationComponent(NearbyPlacesModule(this))
    }

    private fun getApplicationComponent(): ApplicationComponent {
        return (application as NearbyApplication).applicationComponent
    }
}