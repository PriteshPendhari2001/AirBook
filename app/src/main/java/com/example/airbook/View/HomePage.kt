package com.example.airbook.View

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.navigation.compose.rememberNavController
import com.example.airbook.R
import com.example.airbook.ui.theme.lightGrey
import com.example.airbook.ui.theme.skyblue
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(navController: NavController) {

    val internalNavController = rememberNavController()

    val fireabseUser = Firebase.auth.currentUser
    val userName = remember { mutableStateOf("") }

    // Fetch user details when composable is first launched
    LaunchedEffect(Unit) {
        fireabseUser?.let { user ->
            userName.value = user.displayName ?: "Guest" //if name not found then default string shown
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(color = lightGrey)) {

        //Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp))
                .background(skyblue)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_user),//User Images
                        contentDescription = "User Image",
                        modifier = Modifier
                            .size(55.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Hello 👋",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                        Text(
                            text = userName.value,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.weather2), // weather icon
                                contentDescription = "Weather Icon",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("35°", color = Color.White, fontSize = 12.sp)
                        }
                    }
                }

                IconButton(onClick = { /* Notification click */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.notification_bell),
                        contentDescription = "Notifications",
                        tint = Color.White,
                        modifier = Modifier
                            .size(30.dp)
                            .background(Color.White.copy(alpha = 0.2f), shape = CircleShape)
                            .padding(6.dp)
                    )
                }
            }
        }

        FlightReservationScreen()

        Spacer(modifier = Modifier.height(10.dp))

        FlightScreen()

    }
}