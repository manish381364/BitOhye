package com.littlebit.bitohye.ui.screens.settings

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseAuth
import com.littlebit.bitohye.navigation.Screens


@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview(){
    SettingsScreen()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun SettingsScreen(navController: NavHostController = rememberAnimatedNavController()) {
    Scaffold(topBar = { SettingTopBar(navController) }) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(settingsItemInformation) { index, item ->
                when (index) {
                    0 -> {
                        ProfileInformation(
                            title = item.title,
                            description = item.description!!,
                            leadingIcon = Icons.Filled.AccountCircle
                        )
                    }
                    settingsItemInformation.size - 1 -> {
                        ByLittleBit()
                    }
                    else -> {
                        ListItem(
                            modifier = Modifier.clickable {
                                if (item.title == "Chats") {
                                    navController.navigate(Screens.SettingsChat.route)
                                }
                                else if(item.title=="Sign Out"){
                                    FirebaseAuth.getInstance().signOut()
                                    navController.navigate(Screens.PhoneAuthScreen.route){
                                        popUpTo(Screens.HomeScreen.route){
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                }
                            },
                            headlineText = { Text(text = item.title) },
                            supportingText = { item.description?.let { Text(text = it) } },
                            leadingContent = {
                                item.leadingIcon?.let {
                                    Icon(
                                        imageVector = it,
                                        contentDescription = null
                                    )
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}
