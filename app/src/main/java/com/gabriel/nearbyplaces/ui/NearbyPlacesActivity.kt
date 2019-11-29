package com.gabriel.nearbyplaces.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.gabriel.nearbyplaces.R
import com.gabriel.nearbyplaces.utils.BottomNavController
import com.gabriel.nearbyplaces.utils.setUpNavigation
import com.google.android.material.bottomnavigation.BottomNavigationView

class NearbyPlacesActivity : BaseActivity(), BottomNavController.NavGraphProvider,
    BottomNavController.OnNavigationGraphChanged,
    BottomNavController.OnNavigationReselectedListener {

    private lateinit var bottomNavigationView: BottomNavigationView

    private val bottomNavController by lazy(LazyThreadSafetyMode.NONE) {
        BottomNavController(
            this,
            R.id.main_nav_host_fragment,
            R.id.nav_restaurant,
            this,
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setUpNavigation(bottomNavController, this)
        if (savedInstanceState == null) {
            bottomNavController.onNavigationItemSelected()
        }

        setToolBarTitle(getString(R.string.default_screen_title))
    }

    override fun getNavGraphId(itemId: Int): Int = when (itemId) {
        R.id.nav_restaurant -> {
            R.navigation.nav_restaurant
        }

        R.id.nav_bar -> {
            R.navigation.nav_bar
        }

        R.id.nav_cafe -> {
            R.navigation.nav_cafe
        }

        else -> {
            R.navigation.nav_restaurant
        }
    }

    override fun onBackPressed() = bottomNavController.onBackPressed()

    override fun onGraphChanged() {
        disposeActiveOperations()
    }

    override fun setToolBarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun disposeActiveOperations() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onReselectNavItem(navController: NavController, fragment: Fragment) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
