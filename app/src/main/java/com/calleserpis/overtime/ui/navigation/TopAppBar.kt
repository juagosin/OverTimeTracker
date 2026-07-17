package com.calleserpis.overtime.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.calleserpis.overtime.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverTimeTopAppBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isSummaryRoute = currentDestination.matchesRoute(Screens.Summary::class)
    val isHomeRoute = currentDestination.matchesRoute(Screens.Home::class)
    val isListRoute = currentDestination.matchesRoute(Screens.List::class)
    val isTopLevelRoute = isSummaryRoute || isHomeRoute || isListRoute
    var colorTopBar = topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
    )

    val title = when {
        isSummaryRoute -> stringResource(R.string.titleResumenScreen)
        isHomeRoute -> stringResource(R.string.titleCalendarioScreen)
        isListRoute -> stringResource(R.string.titleListadoScreen)
        else -> stringResource(R.string.app_name)
    }

    TopAppBar(
        title =
            {
                Text(
                    text = title,
                    modifier = Modifier.padding(12.dp)
                )

            },
        colors = colorTopBar,

        navigationIcon = {

            if (!isTopLevelRoute) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.navigation_back),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

    )

}
