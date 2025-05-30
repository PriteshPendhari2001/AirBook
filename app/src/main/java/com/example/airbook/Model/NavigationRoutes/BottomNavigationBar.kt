package com.example.airbook.Model.NavigationRoutes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.airbook.R
import com.example.airbook.ui.theme.waterBlue


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem(
            route = NavigationRoutes.HomeScreen.routes,
            icon = R.drawable.home,
            label = "Home"
        ),
        NavigationItem(
            route = NavigationRoutes.TicketScreen.routes,
            icon = R.drawable.ticket_flight,
            label = "Tickets"
        ),
        NavigationItem(
            route = NavigationRoutes.ProfileScreen.routes,
            icon = R.drawable.user,
            label = "Profile"
        )
    )

    NavigationBar(
        containerColor = waterBlue,
        contentColor = Color.Black,
        modifier = Modifier.clip(RoundedCornerShape(12.dp))
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.label) },
                label = { Text(text = item.label) },
                selected = currentRoute == item.route,
                
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reelecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class NavigationItem(
    val route: String,
    val icon: Int,
    val label: String
)