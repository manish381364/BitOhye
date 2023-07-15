package com.littlebit.bitohye.ui.screens.settings.settingschat

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Backup
import androidx.compose.material.icons.rounded.BrightnessMedium
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Wallpaper
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.littlebit.bitohye.ui.theme.OhyeTheme

@Composable
@Preview(showBackground = true)
fun SPreview() {
    OhyeTheme {
        SettingsChatScreen()
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SettingsChatScreen(navController: NavHostController = rememberAnimatedNavController()) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colorScheme.primary,
        darkIcons = isSystemInDarkTheme()
    )
    val showAlertDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    SelectThemeAlert(showAlertDialog){
        Toast.makeText(context, "Theme changed to $it", Toast.LENGTH_SHORT).show()
    }
    val displayItems: List<RowItem> = List(2) { index ->
        when (index) {
            1 -> RowItem(
                leadingIcon = Icons.Rounded.Wallpaper,
                title = "Wallpaper",
            )
            else -> RowItem(
                leadingIcon = Icons.Rounded.BrightnessMedium,
                title = "Theme",
                description = if (isSystemInDarkTheme()) "Dark" else "Light",
                onClick = {
                    showAlertDialog.value = true
                }
            )
        }
    }
    val chatSettingsItems: List<RowItem> = List(3) { index ->
        when (index) {
            0 -> RowItem(
                trailingSwitch = true,
                title = "Enter is send",
                description = "Enter key will send your message",
            )

            1 -> RowItem(
                trailingSwitch = true,
                title = "Media visibility",
                description = "Show newly downloaded media in your device's gallery",
            )

            else -> RowItem(
                title = "Font size",
                description = "Medium",
            )
        }
    }
    val archivedChatsItems: List<RowItem> = List(1) { index ->
        when (index) {
            0 -> RowItem(
                trailingSwitch = true,
                title = "Keep chats archived",
                description = "Archive chats will remain archived when you receive new message",
            )

            else -> RowItem(
                title = "Font size",
                description = "Medium",
            )
        }
    }
    Scaffold(
        topBar = {
            SettingsChatTopBar(navController)
        },

        ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(16.dp))
            DisplayComponent(navController, title = "Display", rowItems = displayItems)
            Divider(thickness = 0.5.dp)
            DisplayComponent(
                navController,
                title = "Chat settings",
                rowItems = chatSettingsItems,
                Modifier.padding(8.dp)
            )
            Divider(thickness = 0.5.dp)
            DisplayComponent(
                navController,
                title = "Archived Chats",
                rowItems = archivedChatsItems,
                Modifier.padding(8.dp)
            )
            Divider(thickness = 0.5.dp)
            RowItem(
                title = "Chat backup",
                navController = navController,
                leadingIcon = Icons.Rounded.Backup
            )
            Spacer(Modifier.height(8.dp))
            RowItem(
                title = "Chat history",
                navController = navController,
                leadingIcon = Icons.Rounded.History
            )
        }
    }
}
