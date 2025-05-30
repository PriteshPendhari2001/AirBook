package com.example.airbook.View.SplashScreen


import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.airbook.Model.NavigationRoutes.NavigationRoutes
import com.example.airbook.R
import com.example.airbook.ui.theme.antonFontFamily
import com.example.airbook.ui.theme.darkBlue
import com.example.airbook.ui.theme.lightBlue
import com.example.airbook.ui.theme.ranchers
import com.example.airbook.ui.theme.waterBlue
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember { Animatable(0f) }
    var progress by remember { mutableStateOf(0f) }
    val currentUser = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(key1 = true) {
        // Animate the logo scale
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1500,
                easing = { OvershootInterpolator(0.5f).getInterpolation(it) }
            )
        )

        // Simulate loading progress
        while (progress < 1f) {
            delay(50)
            progress += 0.02f
        }

        //i use this to check if user is allready login then he directly to access HomePage if is not then user redirect to signupPage
        if (currentUser != null){
            navController.navigate(NavigationRoutes.HomeScreen.routes) {
                popUpTo(NavigationRoutes.SplashScreen.routes) { inclusive = true }
            }
        }else{
            navController.navigate(NavigationRoutes.SignUpScreen.routes){
                popUpTo(NavigationRoutes.SplashScreen.routes) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = lightBlue)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.applogo),
                    contentDescription = "logo",
                    modifier = Modifier.scale(scale.value)
                )
                Text(
                    text = "AirBook",
                    style = TextStyle(
                        fontSize = 28.sp,
                        color = darkBlue
                    ),
                    fontFamily = ranchers
                )
            }

            ImageLoadingBar(
                progress = progress,
                imageForPercentage = { currentProgress ->
                    LoadingImageSelector(currentProgress)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}



@Composable
fun LoadingImageSelector(progress: Float) {
    val loadingImages = listOf(
        R.drawable.loading_seven,
        R.drawable.loading_one,
        R.drawable.loading_two,
        R.drawable.loading_three,
        R.drawable.loading_four,
        R.drawable.loading_five,
        R.drawable.loading_six,
        R.drawable.loading_seven,
        R.drawable.loading_eight
    )
    val progressSteps = listOf(0f, 0.125f, 0.25f, 0.375f, 0.5f, 0.625f, 0.75f, 0.875f, 1.0f)

    val imageIndex = remember(progress) {
        progressSteps.indexOfLast { progress >= it }
    }

    val imageResourceId = loadingImages.getOrNull(imageIndex) ?: R.drawable.loading_seven // Default if out of bounds

    Image(
        painter = painterResource(id = imageResourceId),
        contentDescription = "Loading Image",
        modifier = Modifier.size(50.dp)
    )
}

@Composable
fun ImageLoadingBar(
    progress: Float,
    modifier: Modifier = Modifier,
    imageForPercentage: @Composable (Float) -> Unit
) {
    animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the image based on the progress
        imageForPercentage(progress)

        Spacer(modifier = Modifier.height(16.dp))

    }
}