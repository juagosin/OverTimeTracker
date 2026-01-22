package com.calleserpis.overtime.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.calleserpis.overtime.ui.screens.calendar.CalendarScreen
import com.calleserpis.overtime.ui.screens.detail.DetailScreen
import com.calleserpis.overtime.ui.screens.list.ListScreen

@Composable
fun OverTimeNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route,
        modifier = modifier
    ) {
        composable(Screens.Home.route) {
            CalendarScreen(
                onNavigateToDetail = { navController.navigate(Screens.Detail.route) }
            )
        }
        composable(Screens.List.route) {
            ListScreen(
                onNavigateToDetail = { navController.navigate(Screens.Detail.route) }
            )
        }
        composable(Screens.Detail.route) {
            DetailScreen()
        }

    }

}