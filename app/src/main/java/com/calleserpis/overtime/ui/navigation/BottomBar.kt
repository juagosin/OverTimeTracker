package com.calleserpis.overtime.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun OverTimeBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute : String? = navBackStackEntry?.destination?.route

    NavigationBar() {
        NavigationBarItem(
            label = {
                Text(
                    text = "Calendario"
                )
            },
            selected = currentRoute == Screens.Home.route,
            onClick = {
                navController.navigate(Screens.Home.route)
            },
            icon = {
                Icon( Screens.Home.icon, contentDescription = "")
            }
        )
        NavigationBarItem(
            label = {
                Text(
                    text = "Listado"
                )
            },
            selected = currentRoute == Screens.List.route,
            onClick = {
            //TODO
            //navController.navigate(Screens.List.route)
            },
            icon = {
                Icon( Screens.List.icon, contentDescription = "")
            }
        )

    }


}