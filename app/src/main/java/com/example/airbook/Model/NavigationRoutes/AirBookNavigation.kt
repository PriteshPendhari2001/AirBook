package com.example.airbook.Model.NavigationRoutes


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.airbook.ViewModel.authViewModel
import com.example.airbook.View.HomePage
import com.example.airbook.View.ProfileScreens.EditScreen
import com.example.airbook.View.UserAuthScreen.LoginPage
import com.example.airbook.View.ProfileScreens.ProfileScreen
import com.example.airbook.View.UserAuthScreen.SignUpScreen
import com.example.airbook.View.TicketScreens.TicketScreen
import com.example.airbook.View.SplashScreen.SplashScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AirBookNavigation(authViewModel: authViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            if (navController.currentBackStackEntryAsState().value?.destination?.route in
                listOf(
                    NavigationRoutes.HomeScreen.routes,
                    NavigationRoutes.TicketScreen.routes,
                    NavigationRoutes.ProfileScreen.routes
                )
            ) {
                BottomNavigationBar(navController)
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0) //This removes padding from system windo

    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationRoutes.SplashScreen.routes,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavigationRoutes.SplashScreen.routes) {
                SplashScreen(navController)
            }
            composable(NavigationRoutes.SignUpScreen.routes) {
                SignUpScreen(navController, authViewModel)
            }
            composable(NavigationRoutes.LoginScreen.routes) {
                LoginPage(navController, authViewModel)
            }
            composable(NavigationRoutes.HomeScreen.routes) {
                HomePage(navController)
            }
            composable(NavigationRoutes.TicketScreen.routes) {
                TicketScreen(navController)
            }
            composable(NavigationRoutes.ProfileScreen.routes) {
                ProfileScreen(navController,authViewModel)
            }

            composable(NavigationRoutes.EditProfile.routes){
                EditScreen(navController,authViewModel)
            }
        }
    }
}
