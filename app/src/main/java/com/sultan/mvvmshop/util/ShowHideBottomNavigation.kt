package com.sultan.mvvmshop.util

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sultan.mvvmshop.R
import com.sultan.mvvmshop.activityes.ShoppingActivity

fun Fragment.hideBottomNavigation(){
    val bottomNavigation =
        (activity as ShoppingActivity).findViewById<BottomNavigationView>(R.id.bottomNavigation)
    bottomNavigation.visibility = View.GONE
}
fun Fragment.showBottomNavigation(){
    val bottomNavigation =
        (activity as ShoppingActivity).findViewById<BottomNavigationView>(R.id.bottomNavigation)
    bottomNavigation.visibility = View.VISIBLE
}