package com.littlebit.bitohye.ui.screens.homescreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@SuppressLint("NewApi")
@RequiresApi(Build.VERSION_CODES.R)
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class
)
@Preview(showBackground = true)
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController()
) {
    val systemUiController = rememberSystemUiController()
    val isDropdownExpanded = rememberSaveable { mutableStateOf(false) }
    val isVisibleSearchIcon = rememberSaveable { mutableStateOf(true) }
    val sugestionButtonsInfo = suggestionForButtons()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    if (isVisibleSearchIcon.value) {
        systemUiController.setSystemBarsColor(
            color = MaterialTheme.colorScheme.primary
        )
    } else {
        systemUiController.setSystemBarsColor(
            color = MaterialTheme.colorScheme.surface
        )
    }
    AnimatedVisibility(
        visible = !isVisibleSearchIcon.value,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ),
    ) {
        SearchScreen(isVisibleSearchIcon)
    }
    AnimatedVisibility(
        isVisibleSearchIcon.value,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ),
    ) {
        Scaffold(
            topBar = {
                HomeTopBar(
                    isDropdownExpanded,
                    isVisibleSearchIcon,
                    navController,
                    scrollBehavior
                )
            },
            floatingActionButton = {
                FloatingButton(isVisibleSearchIcon, navController)
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                HomeScreenContent(
                    isVisibleSearchIcon,
                    sugestionButtonsInfo,
                    navController
                )
            }
        }
    }
}










