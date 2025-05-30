package com.example.airbook.Model.NavigationRoutes

import androidx.annotation.DrawableRes
import com.example.airbook.R

open class NavigationRoutes(val routes : String){

    object SplashScreen : NavigationRoutes(routes = "SplashScreen")
    object SignUpScreen : NavigationRoutes(routes = "SignUpScreen")
    object LoginScreen : NavigationRoutes(routes = "LoginScreen")

    //BottomNavigationRoutes
    object HomeScreen : NavigationRoutes(routes = "HomeScreen")
    object TicketScreen : NavigationRoutes(routes = "TicketScreen")
    object ProfileScreen : NavigationRoutes(routes = "ProfileScreen")
    object EditProfile : NavigationRoutes(routes = "EditProfile")
}
