package com.calleserpis.overtime.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.calleserpis.overtime.R

@Composable
fun OverTimeBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute: String? = navBackStackEntry?.destination?.route

    NavigationBar() {
        NavigationBarItem(
            label = {
                Text(
                    text = stringResource(R.string.titleCalendarioScreen)
                )
            },
            selected = currentRoute?.contains("Home") == true,
            onClick = {
                navController.navigate(Screens.Home)
            },
            icon = {
                Icon(Screens.Home.icon, contentDescription = "")
            }
        )
        NavigationBarItem(
            label = {
                Text(
                    text = stringResource(R.string.titleListadoScreen)
                )
            },
            selected = currentRoute?.contains("List") == true,
            onClick = {

                navController.navigate(Screens.List)
            },
            icon = {
                Icon(Screens.List.icon, contentDescription = "")
            }
        )

    }


}