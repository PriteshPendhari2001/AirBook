package com.example.airbook.View.ProfileScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.airbook.ViewModel.authViewModel
import com.example.airbook.Model.NavigationRoutes.NavigationRoutes
import com.example.airbook.R
import com.example.airbook.ui.theme.skyblue
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun ProfileScreen(navController: NavController, authViewModel: authViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD8E7EB))
    ) {
        TopSection(navController)
        ProfileCard(navController, authViewModel)
    }
}

@Composable
fun TopSection(navController: NavController) {

    val fireabseUser = Firebase.auth.currentUser
    val userName = remember { mutableStateOf("") }

    // Fetch user details when composable is first launched
    LaunchedEffect(Unit) {
        fireabseUser?.let { user ->
            userName.value =
                user.displayName ?: "Guest" //if name not found then default string shown
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(skyblue),
        contentAlignment = Alignment.TopCenter
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { /* TODO: Back */navController.navigate(NavigationRoutes.HomeScreen.routes) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            IconButton(onClick = { /* TODO: Notification */ }) {
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = "Notification",
                    tint = Color.White
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 60.dp)
        ) {
            // Profile Image
            Image(
                painter = painterResource(R.drawable.profile_user),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .border(4.dp, Color.White, CircleShape)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = userName.value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}


@Composable
fun ProfileOption(
    icon: Int,
    title: String,
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)  // Make the whole row clickable
            .padding(vertical = 12.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(text = title, color = Color.White)

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Composable
fun ProfileCard(navController: NavController, authViewModel: authViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = skyblue,
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ProfileOption(
                icon = R.drawable.user,
                title = "Profile Details",
                onClick = { navController.navigate(NavigationRoutes.EditProfile.routes)}
            )

            ProfileOption(
                icon = R.drawable.notification,
                title = "Notification",
                onClick = { /*  navigate */ }
            )

            ProfileOption(
                icon = R.drawable.languages,
                title = "Language",
                onClick = { /*  navigate */ }
            )

            ProfileOption(
                icon = R.drawable.settings,
                title = "Settings",
                onClick = { /*  navigate */ }
            )

            ProfileOption(
                icon = R.drawable.logout,
                title = "Logout",
                onClick = {
                    authViewModel.signout()
                    navController.navigate(NavigationRoutes.SignUpScreen.routes) { popUpTo(0) }
                }
            )
        }
    }
}