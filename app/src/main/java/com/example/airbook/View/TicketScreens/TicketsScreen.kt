package com.example.airbook.View.TicketScreens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import com.example.airbook.R
import com.example.airbook.ui.theme.darkBlue
import com.example.airbook.ui.theme.lightGrey
import com.example.airbook.ui.theme.skyblue
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun TicketScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // ticket content...
            userProfile()
            TicketBook()
        }

        // Download button
        Button(
            onClick = { /* Handle download */ },
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(darkBlue)
        ){
            Text("Download", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}


@Composable
fun TicketBook() {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color(0xFFFDFCF9), shape = RoundedCornerShape(16.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {

            // Header Time Info
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text("02:00 AM", fontWeight = FontWeight.Bold)
                    Text("PHL CRK", color = Color.Gray)
                }
                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                Column(horizontalAlignment = Alignment.End) {
                    Text("09:00 AM", fontWeight = FontWeight.Bold)
                    Text("JPN TKY", color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(thickness = 1.dp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(8.dp))

            // Duration
            Text(
                "- 7 Hours -",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.Gray,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Destination and Price
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("TOKYO", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Text("$200", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Passenger Info Grid
            Column {
                InfoRow("Passenger Name:", "Pritesh Vinod Pendhari")
                InfoRow("Flight:", "ABC 123")
                Spacer(modifier = Modifier.height(4.dp))
                InfoRow("Seat:", "2B")
                InfoRow("Class:", "Economy")
                InfoRow("Terminal:", "4C")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Barcode
            Text(
                "SCAN HERE",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color.White)
            ) {
                // barcode image using Coil or other image lib
                Icon(painter = painterResource(R.drawable.barcode), contentDescription = null)
            }
        }

    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray)
        Text(value, fontWeight = FontWeight.Medium)
    }
}


//just copy previous
@Composable
fun userProfile() {

    Column(modifier = Modifier.fillMaxSize().background(color = lightGrey)) {

        val fireabseUser = Firebase.auth.currentUser
        val userName = remember { mutableStateOf("") }

        // Fetch user details when composable is first launched
        LaunchedEffect(Unit) {
            fireabseUser?.let { user ->
                userName.value =
                    user.displayName ?: "Guest" //if name not found then default string shown
            }
        }

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
    }
}