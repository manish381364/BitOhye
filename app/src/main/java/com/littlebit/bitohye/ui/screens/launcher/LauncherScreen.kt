package com.littlebit.bitohye.ui.screens.launcher


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseInBack
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.littlebit.bitohye.R
import com.littlebit.bitohye.navigation.Screens
import com.littlebit.bitohye.ui.screens.settings.ByLittleBit
import kotlinx.coroutines.delay


@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview(showBackground = true)
fun LauncherScreen(
    navController: NavHostController = rememberNavController()
) {
    var visible by remember { mutableStateOf(true) }
    val scale by animateFloatAsState(
        if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 3000, easing = EaseInBack)
    )

    Surface(
        color = Color.Transparent,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(Modifier.align(Alignment.Center)) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(100.dp)
                        .offset(y = (-64).dp)
                        .scale(scale)
                        .clip(RoundedCornerShape(30.dp))
                )
                Image(
                    painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .offset(y = (-64).dp)
                        .scale(scale)
                        .clip(RoundedCornerShape(30.dp))
                )
            }
            Box(Modifier.align(Alignment.BottomCenter)) {
                ByLittleBit()
            }
        }
    }

    LaunchedEffect(key1 = true) {
        visible = false
        delay(2000)
        if (FirebaseAuth.getInstance().currentUser != null) {
            if (FirebaseAuth.getInstance().currentUser?.displayName != null)
                navController.navigate(Screens.HomeScreen.route) {
                    popUpTo(Screens.LauncherScreen.route) {
                        inclusive = true
                    }
                }
            else {
                navController.navigate(Screens.SetProfileScreen.route) {
                    popUpTo(Screens.LauncherScreen.route) {
                        inclusive = true
                    }
                }
            }

        } else {
            navController.navigate(Screens.PhoneAuthScreen.route) {
                popUpTo(Screens.LauncherScreen.route) {
                    inclusive = true
                }
            }
        }
    }
}
