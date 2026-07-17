package com.calleserpis.overtime.ui.navigation

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import kotlin.reflect.KClass

fun NavDestination?.matchesRoute(route: KClass<*>): Boolean {
    return this?.hasRoute(route) == true
}

fun NavHostController.navigateToTopLevel(screen: Screens) {
    navigate(screen) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
