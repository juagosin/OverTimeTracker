package com.calleserpis.overtime.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DensitySmall
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {
    abstract val route: String


    @Serializable
    data object Summary: Screens(){
        override val route: String = "summary"
        val icon: ImageVector = Icons.Default.BarChart
    }

    @Serializable
    data object Home: Screens(){
        override val route: String = "home"
        val icon: ImageVector = Icons.Default.CalendarMonth
    }

    @Serializable
    data object List: Screens(){
        override val route: String = "list"
        val icon: ImageVector = Icons.Default.DensitySmall
    }

    @Serializable
    data class Detail( val recordId: Long? = null): Screens(){
        override val route: String = "detail"
    }
}
