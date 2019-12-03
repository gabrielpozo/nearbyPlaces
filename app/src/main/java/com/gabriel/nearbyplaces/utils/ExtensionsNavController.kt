package com.gabriel.nearbyplaces.utils

import android.view.MenuItem
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Created by Gabriel Pozo Guzman on 2019-12-01.
 */

private var indexToRemove = 0
const val menuItems = 4

fun BottomNavigationView.setUpNavigation(
    bottomNavController: BottomNavController,
    onReselectListener: BottomNavController.OnNavigationReselectedListener
) {
    setOnNavigationItemSelectedListener {
        bottomNavController.onNavigationItemSelected(it.itemId)
        bottomNavController.setTitleToolbar(it)
    }

    setOnNavigationItemReselectedListener {
        bottomNavController.fragmentManager
            .findFragmentById(bottomNavController.containerId)!!
            .childFragmentManager
            .fragments.firstOrNull()?.let { fragment ->
            onReselectListener.onReselectNavItem(
                bottomNavController.activity.findNavController(
                    bottomNavController.containerId
                ),
                fragment
            )
        }
    }

    bottomNavController.setOnNavigationChanged { itemId ->
        menu.findItem(itemId).isChecked = true
    }
}

fun ArrayList<String>.handleTitleOnToolbar(menuItem: MenuItem) {
    if (size < menuItems) {
        if (!contains(menuItem.toString()) || (menuItem.toString() == this[0])) {
            add(menuItem.toString())
        } else {
            menuHandler(menuItem)
        }
    } else {
        menuHandler(menuItem)
    }
}

private fun ArrayList<String>.menuHandler(menuItem: MenuItem) {
    this.forEachIndexed { index, value ->
        if (menuItem.toString() == value) {
            indexToRemove = index
            return@forEachIndexed
        }
    }
    removeAt(indexToRemove)
    add(menuItem.toString())
}

fun ArrayList<String>.removeTitleFromToolbar(): String {
    if (size > 0) removeAt(this.size - 1)
    return if (this.isNotEmpty()) this.last() else "Restaurants"

}
