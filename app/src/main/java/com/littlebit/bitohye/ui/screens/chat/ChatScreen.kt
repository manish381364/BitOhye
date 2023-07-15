package com.littlebit.bitohye.ui.screens.chat


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


@OptIn(
    ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class
)
@Preview(showBackground = true)
@Composable
fun ChatScreen(navHostController: NavHostController = rememberAnimatedNavController()) {

    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)
    val showAttachmentRow = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            ChatTopBar(
                navHostController,
                scrollBehavior = scrollBehavior,
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            ChatBody()
            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                ChattingOptions(showAttachmentRow = showAttachmentRow)
                AnimatedVisibility(
                    showAttachmentRow.value,
                    enter = slideInVertically(
                        initialOffsetY = { x-> x },
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ),
                ) {
                    AttachmentsListButtons()
                }
            }
        }
    }
}









