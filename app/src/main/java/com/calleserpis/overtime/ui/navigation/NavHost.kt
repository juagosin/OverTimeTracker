package com.calleserpis.overtime.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.calleserpis.overtime.ui.screens.calendar.CalendarScreen
import com.calleserpis.overtime.ui.screens.detail.DetailScreen
import com.calleserpis.overtime.ui.screens.list.ListScreen

@Composable
fun OverTimeNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home,
        modifier = modifier
    ) {
        composable<Screens.Home> {
            CalendarScreen(
                onNavigateToDetail = { recordId -> navController.navigate(Screens.Detail(recordId = recordId)) }
            )
        }
        composable<Screens.List> {
            ListScreen(
                onNavigateToDetail = { recordId -> navController.navigate(Screens.Detail(recordId = recordId)) }
            )
        }
        composable<Screens.Detail> { backStackEntry ->
            val detail: Screens.Detail = backStackEntry.toRoute()

            DetailScreen(
                recordId = detail.recordId,
                onOverTimeSaved = {
                    navController.popBackStack()
                },
                onOverTimeDeleted = {
                    navController.popBackStack()
                }
            )

        }

    }

}