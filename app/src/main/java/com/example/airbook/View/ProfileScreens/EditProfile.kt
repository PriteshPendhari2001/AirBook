package com.example.airbook.View.ProfileScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.airbook.ViewModel.authViewModel
import com.example.airbook.ui.theme.lightGrey
import com.example.airbook.ui.theme.ranchers
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


@Composable

fun EditScreen(navController: NavController, authViewModel: authViewModel){


    // State variables
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var currentPassword by remember { mutableStateOf("") }

    val currentUser = Firebase.auth.currentUser
    val db = Firebase.firestore

    // Fetch user data from Firestore
    LaunchedEffect(Unit) {
        currentUser?.uid?.let { userId ->
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    document.getString("name")?.let { name = it }
                    document.getString("password")?.let { currentPassword = it }
                    currentUser.email?.let { email = it }
                }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(lightGrey).padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // Header
        Text(
            "Profile Details",
            fontFamily = ranchers,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = currentPassword,
            onValueChange = { currentPassword = it },
            label = { Text("Current Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )


        // Cancel Button

        Button(onClick = {navController.popBackStack() }){
            Text("Cancel")
        }
        }
    }