package com.littlebit.bitohye.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.littlebit.bitohye.ui.screens.camera.CameraScreen
import com.littlebit.bitohye.ui.screens.chat.ChatScreen
import com.littlebit.bitohye.ui.screens.contact.SelectContactScreen
import com.littlebit.bitohye.ui.screens.countrylist.CountryListScreen
import com.littlebit.bitohye.ui.screens.homescreen.HomeScreen
import com.littlebit.bitohye.ui.screens.launcher.LauncherScreen
import com.littlebit.bitohye.ui.screens.newgroup.NewGroupScreen
import com.littlebit.bitohye.ui.screens.phoneauth.PhoneAuthScreen
import com.littlebit.bitohye.ui.screens.profile.ProfileScreen
import com.littlebit.bitohye.ui.screens.setprofile.SetProfileScreen
import com.littlebit.bitohye.ui.screens.settings.SettingsScreen
import com.littlebit.bitohye.ui.screens.settings.settingschat.SettingsChatScreen

@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph() {
    val navController = rememberAnimatedNavController()
    val country: MutableState<String> = remember { mutableStateOf("US[]+1") }
    AnimatedNavHost(navController = navController, startDestination = Screens.LauncherScreen.route)
    {
        composable(
            Screens.PhoneAuthScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(600))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(800))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(600))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(800))
            },
        ) {
            PhoneAuthScreen(navController, country)
        }

        composable(
            Screens.SetProfileScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(600))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(800))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(600))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(800))
            },
        ) {
            SetProfileScreen(navController)
        }


        composable(
            Screens.CountryListScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(600))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(800))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(600))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(800))
            },
        ) {
            CountryListScreen(navController, country)
        }


        composable(
            Screens.HomeScreen.route,
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(800))
            },
        ) {
            HomeScreen(navController)
        }


        composable(Screens.LauncherScreen.route) {
            LauncherScreen(navController)
        }
        composable(
            Screens.SettingsScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(400))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(200))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(400))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300))
            }
        ) {
            SettingsScreen(navController)
        }
        composable(
            Screens.SelectContactScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(400))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(200))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(400))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(200))
            }
        ){
            SelectContactScreen(navController)
        }

        composable(
            Screens.ChatScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(400))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(200))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(400))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(200))
            }
        ){
            ChatScreen(navController)
        }

        composable(
            Screens.SettingsChat.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(400))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(200))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(400))
            },
        ){
            SettingsChatScreen(navController)
        }

        composable(
            Screens.CameraScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(400))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(200))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(400))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(200))
            }
        ){
            CameraScreen(navController)
        }

        composable(
            Screens.SetProfileScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(400))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(200))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(400))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(200))
            }
        ){
            SetProfileScreen(navController)
        }

        composable(
            Screens.NewGroupScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(400))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(200))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(400))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(200))
            }
        ){
            NewGroupScreen()
        }


        composable(
            Screens.ProfileScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(400))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(200))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(400))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(200))
            }
        ){
            ProfileScreen(navController)
        }
    }
}


sealed class Screens(val route: String) {
    object PhoneAuthScreen : Screens("phoneAuthScreen")
    object CountryListScreen : Screens("countryListScreen")
    object HomeScreen : Screens("homeScreen")
    object LauncherScreen : Screens("launcherScreen")
    object SettingsScreen : Screens("settingsScreen")
    object SelectContactScreen : Screens("selectContactScreen")
    object ChatScreen : Screens("chatScreen")
    object SettingsChat: Screens("settingsChat")
    object CameraScreen: Screens("cameraScreen")
    object SetProfileScreen: Screens("setProfileScreen")
    object NewGroupScreen: Screens("newGroupScreen")
    object ProfileScreen: Screens("profileScreen")
}