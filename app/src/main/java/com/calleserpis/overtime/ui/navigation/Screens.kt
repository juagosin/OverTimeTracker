package com.calleserpis.overtime.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DensitySmall
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {
    abstract val route: String
    abstract val icon: ImageVector

    @Serializable
    data object Home: Screens(){
        override val route: String = "home"
        override val icon: ImageVector = Icons.Default.CalendarMonth
    }

    @Serializable

    data object List: Screens(){
        override val route: String = "list"
        override val icon: ImageVector = Icons.Default.DensitySmall
    }
}