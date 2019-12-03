package com.gabriel.nearbyplaces.utils

import android.app.Activity
import android.content.Context
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.gabriel.nearbyplaces.R
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Created by Gabriel Pozo Guzman on 2019-11-29.
 */

class BottomNavController(
    context: Context,
    @IdRes val containerId: Int,
    @IdRes val appStartDestinationId: Int,
    private val graphChangeListener: OnNavigationGraphChanged?,
    private val navGraphProvider: NavGraphProvider
) {
    lateinit var activity: Activity
    lateinit var fragmentManager: FragmentManager
    lateinit var navItemChangeListener: OnNavigationItemChanged
    private val navigationBackStack: BackStack = BackStack.of(appStartDestinationId)
    private lateinit var hostFragment: NavHostFragment
    private var menuItemList = ArrayList<String>()

    init {
        if (context is Activity) {
            activity = context
            fragmentManager = (activity as FragmentActivity).supportFragmentManager
        }

        menuItemList.add(activity.getString(R.string.default_screen_title))
    }

    fun onNavigationItemSelected(itemId: Int = navigationBackStack.last()): Boolean {
        hostFragment = NavHostFragment.create(
            navGraphProvider.getNavGraphId(itemId)
        )

        val fragment =
            fragmentManager.findFragmentByTag(itemId.toString()) ?: hostFragment

        fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.fade_out
            ).replace(containerId, fragment, itemId.toString())
            .addToBackStack(null)
            .commit()

        navigationBackStack.moveLast(itemId)
        navItemChangeListener.onItemChanged(itemId)
        graphChangeListener?.onGraphChanged()

        return true
    }

    fun onBackPressed() {
        val childFragmentManager =
            fragmentManager.findFragmentById(containerId)!!.childFragmentManager
        when {
            childFragmentManager.popBackStackImmediate() -> { }

            navigationBackStack.size > 1 -> {
                navigationBackStack.removeLast()
                onNavigationItemSelected()
            }

            navigationBackStack.last() != appStartDestinationId -> {
                navigationBackStack.removeLast()
                navigationBackStack.add(0, appStartDestinationId)
                onNavigationItemSelected()
            }

            else -> activity.finish()
        }

        graphChangeListener?.setToolBarTitle(menuItemList.removeTitleFromToolbar())
    }

    private class BackStack : ArrayList<Int>() {

        companion object {
            fun of(vararg elements: Int): BackStack {
                val b = BackStack()
                b.addAll(elements.toTypedArray())
                return b
            }
        }

        fun removeLast() = removeAt(size - 1)
        fun moveLast(item: Int) {
            remove(item)
            add(item)
        }
    }

    interface OnNavigationItemChanged {
        fun onItemChanged(itemId: Int)
    }

    fun setOnNavigationChanged(listener: (itemId: Int) -> Unit) {
        this.navItemChangeListener = object : OnNavigationItemChanged {
            override fun onItemChanged(itemId: Int) {
                listener.invoke(itemId)
            }
        }
    }

    fun setTitleToolbar(menuItem: MenuItem): Boolean {
        menuItemList.handleTitleOnToolbar(menuItem)
        graphChangeListener?.setToolBarTitle(menuItem.toString())
        return true
    }

    interface NavGraphProvider {
        @NavigationRes
        fun getNavGraphId(itemId: Int): Int
    }

    interface OnNavigationGraphChanged {
        fun onGraphChanged()
        fun setToolBarTitle(title: String)
    }

    interface OnNavigationReselectedListener {
        fun onReselectNavItem(navController: NavController, fragment: Fragment)
    }
}

