package com.littlebit.bitohye.ui.screens.camera

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview
fun CameraScreenPreview() {
    CameraScreen(navController = rememberAnimatedNavController())
}

@Composable
fun CameraScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(navController)
        Row(
            Modifier
                .align(Alignment.BottomCenter)
                .background(color = Color.Black.copy(0.5f))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            
        }
    }
}



