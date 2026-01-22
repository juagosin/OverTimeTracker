package com.calleserpis.overtime.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun OverTimeScaffold(modifier: Modifier) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            //TODO
        },
        bottomBar = {
            //TODO
        },
        floatingActionButton = {
            //TODO
        }
    ){
        innerPadding ->
        OverTimeNavHost(navController = navController, modifier = Modifier.padding(innerPadding))

    }

}