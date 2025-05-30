package com.example.airbook.View.UserAuthScreen


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.airbook.ViewModel.authViewModel
import com.example.airbook.Model.NavigationRoutes.NavigationRoutes
import com.example.airbook.ui.theme.darkBlue
import com.example.airbook.ui.theme.lightGrey
import com.example.airbook.ui.theme.ranchers


@Composable
fun LoginPage(navController: NavController,authViewModel: authViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value){
        when(authState.value){
            is authViewModel.AuthState.Authenticated -> navController.navigate(NavigationRoutes.HomeScreen.routes)
            is authViewModel.AuthState.Erorr -> Toast.makeText(context,(authState.value as authViewModel.AuthState.Erorr).message,
                Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(lightGrey),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Log In",
            fontFamily = ranchers,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        Button(onClick = {authViewModel.Login(email, password)}, enabled = authState.value != com.example.airbook.ViewModel.authViewModel.AuthState.Loading, modifier = Modifier.fillMaxWidth()) {
            Text("Log In")
        }

        TextButton(onClick = {navController.navigate(NavigationRoutes.SignUpScreen.routes)}, modifier = Modifier.padding(top = 8.dp)) {
            Text("Don't have an account? ")
            Text("Sign Up", color = darkBlue, fontWeight = FontWeight.Bold)
        }
    }
}
