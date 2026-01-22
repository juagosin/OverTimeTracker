package com.calleserpis.overtime.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.calleserpis.overtime.ui.screens.calendar.CalendarScreen

@Composable
fun OverTimeNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route,
        modifier = modifier
    ){
        composable(Screens.Home.route){
            CalendarScreen()
        }

    }

}