package com.calleserpis.overtime.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun OverTimeScaffold(modifier: Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute: String? = navBackStackEntry?.destination?.route
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            OverTimeTopAppBar(navController)
        },
        bottomBar = {
            OverTimeBottomBar(navController)
        },
        floatingActionButton = {
            if(currentRoute != Screens.Detail.route) {
                FloatingActionButton(onClick = { /*TODO*/ },
                    containerColor = colorScheme.primary,
                    contentColor = colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Añadir",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    ){
        innerPadding ->
        OverTimeNavHost(navController = navController, modifier = Modifier.padding(innerPadding))

    }

}