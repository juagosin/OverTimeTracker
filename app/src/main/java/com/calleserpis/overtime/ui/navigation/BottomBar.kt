package com.calleserpis.overtime.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.calleserpis.overtime.R

@Composable
fun OverTimeBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar() {
        NavigationBarItem(
            label = {
                Text(
                    text = stringResource(R.string.titleResumenScreen)
                )
            },
            selected = currentDestination.matchesRoute(Screens.Summary::class),
            onClick = {
                navController.navigateToTopLevel(Screens.Summary)
            },
            icon = {
                Icon(Screens.Summary.icon, contentDescription = stringResource(R.string.titleResumenScreen))
            }
        )
        NavigationBarItem(
            label = {
                Text(
                    text = stringResource(R.string.titleCalendarioScreen)
                )
            },
            selected = currentDestination.matchesRoute(Screens.Home::class),
            onClick = {
                navController.navigateToTopLevel(Screens.Home)
            },
            icon = {
                Icon(Screens.Home.icon, contentDescription = stringResource(R.string.titleCalendarioScreen))
            }
        )
        NavigationBarItem(
            label = {
                Text(
                    text = stringResource(R.string.titleListadoScreen)
                )
            },
            selected = currentDestination.matchesRoute(Screens.List::class),
            onClick = {
                navController.navigateToTopLevel(Screens.List)
            },
            icon = {
                Icon(Screens.List.icon, contentDescription = stringResource(R.string.titleListadoScreen))
            }
        )

    }


}
