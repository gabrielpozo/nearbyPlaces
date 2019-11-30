package com.gabriel.nearbyplaces.ui.places

import androidx.fragment.app.Fragment
import com.gabriel.nearbyplaces.NearbyApplication
import com.gabriel.nearbyplaces.di.ApplicationComponent
import com.gabriel.nearbyplaces.di.subcomponents.PresentationComponent

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */
abstract class BaseFragment : Fragment() {
    protected fun getPresentationComponent(): PresentationComponent {
        return getApplicationComponent().presentationComponent()
    }

    private fun getApplicationComponent(): ApplicationComponent {
        return (activity?.application as NearbyApplication).applicationComponent
    }
}