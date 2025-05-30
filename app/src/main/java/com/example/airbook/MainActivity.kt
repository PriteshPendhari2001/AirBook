package com.example.airbook

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.airbook.ViewModel.authViewModel
import com.example.airbook.Model.NavigationRoutes.AirBookNavigation
import com.example.airbook.ui.theme.AirBookTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AirBookTheme {
                AirBookNavigation(authViewModel())
            }
        }
    }
}
