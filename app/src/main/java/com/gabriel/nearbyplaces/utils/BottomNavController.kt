package com.gabriel.nearbyplaces.utils

import android.app.Activity
import android.content.Context
import android.util.Log
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

        menuItemList.add("Restaurants")
    }

    //:add the NavHost Fragments(also they will have their own backstack)
    fun onNavigationItemSelected(itemId: Int = navigationBackStack.last()): Boolean {
        //Replace a fragment representing a navigation Item
        hostFragment = NavHostFragment.create(
            navGraphProvider.getNavGraphId(itemId)
        )//tag is the string version of the id

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

        //add to the end Backstack<arrayList>
        navigationBackStack.moveLast(itemId)

        //update checked icon
        navItemChangeListener.onItemChanged(itemId)

        //communicate with the activity(ex: pressing back button, notifies the activity to cancel some sort of transaction)
        graphChangeListener?.onGraphChanged()

        return true// notifies the interface that this han been handled
    }

    //on Navigation Host
    fun onBackPressed() {
        val childFragmentManager =
            fragmentManager.findFragmentById(containerId)!!.childFragmentManager
        when {
            childFragmentManager.popBackStackImmediate() -> {
            }

            //Fragment backstack is empty so try to back on the navigation stack
            navigationBackStack.size > 1 -> {
                //remove last item from the backstack
                navigationBackStack.removeLast()
                //update the container with new fragment
                onNavigationItemSelected()
            }
            // if the stack has only one fragment and it's the navigation home
            // we should ensure that application always leave from startDestination
            navigationBackStack.last() != appStartDestinationId -> {
                navigationBackStack.removeLast()
                navigationBackStack.add(0, appStartDestinationId)
                onNavigationItemSelected()
            }

            else -> activity.finish()
        }

        //TODO extract the logic using extensions
        if (menuItemList.size > 0) menuItemList.removeAt(menuItemList.size - 1)
        val menuItem = if (menuItemList.isNotEmpty()) menuItemList.last() else "Restaurants"

        graphChangeListener?.setToolBarTitle(menuItem)
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

    //For setting the check icon in the bottom-nav
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
        //TODO clean the method using extensions
        var indexToRemove = 0
        if (menuItemList.size < 4) {
            if (!menuItemList.contains(menuItem.toString()) || (menuItem.toString() == menuItemList[0])) {
                menuItemList.add(menuItem.toString())
            } else {
                menuItemList.forEachIndexed { index, value ->
                    if (menuItem.toString() == value) {
                        indexToRemove = index
                        return@forEachIndexed
                    }
                }
                menuItemList.removeAt(indexToRemove)
                menuItemList.add(menuItem.toString())
            }
        } else {
            menuItemList.forEachIndexed { index, value ->
                if (menuItem.toString() == value){
                    indexToRemove = index
                    return@forEachIndexed
                }
            }
            menuItemList.removeAt(indexToRemove)
            menuItemList.add(menuItem.toString())
        }


        graphChangeListener?.setToolBarTitle(menuItem.toString())
        return true
    }

    //Get id of each graph
    //ex: R.navigation.nav_blog
    //ex: R.navigation.create_blog
    interface NavGraphProvider {
        @NavigationRes
        fun getNavGraphId(itemId: Int): Int
    }

    //Execute when a nav_graph changes
    //ex: select a new item on the bottom nav
    //ex: Home -> Account
    interface OnNavigationGraphChanged {
        fun onGraphChanged()
        fun setToolBarTitle(title: String)
    }

    interface OnNavigationReselectedListener {
        fun onReselectNavItem(navController: NavController, fragment: Fragment)
    }
}

fun BottomNavigationView.setUpNavigation(
    bottomNavController: BottomNavController,
    onReselectListener: BottomNavController.OnNavigationReselectedListener
) {
    setOnNavigationItemSelectedListener {
        bottomNavController.onNavigationItemSelected(it.itemId)
        // Log.d("Gabriel", "onItem selected $it")
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